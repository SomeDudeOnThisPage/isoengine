package me.buhlmann.isoengine.gfx.render;

import me.buhlmann.isoengine.IsoEngine;
import me.buhlmann.isoengine.gfx.Camera2D;
import me.buhlmann.isoengine.gfx.shader.Shader;
import me.buhlmann.isoengine.gfx.texture.TextureAtlas;
import me.buhlmann.isoengine.gfx.util.IsoMouseMap;
import me.buhlmann.isoengine.input.Input;
import me.buhlmann.isoengine.level.TileChunk;
import me.buhlmann.isoengine.level.TileMap;
import me.buhlmann.isoengine.level.generation.TileChunkGenerator;
import org.joml.Vector2f;
import org.joml.Vector3i;

public class TileMapRenderer extends InstancedRenderer
{
  private Shader tileShader;
  private TextureAtlas textureAtlas;
  private TileMap map;
  private boolean first = true;
  private IsoMouseMap mouseMap = new IsoMouseMap("tileset0_mm");

  private Vector2f localToScreen(float localX, float localY, int w, int h, float ox, float oy, float scale)
  {
    // Global x-coordinate
    float gx = w + (localX - localY) * (int) scale + ox * scale;
    // Global y-coordinate
    float gy = h + (localX + localY) * (int) scale / 2 - oy * scale;

    return new Vector2f(gx, gy);
  }

  private void setMouseUniform(Camera2D camera, TileChunk chunk)
  {
    // Determine mouse location
    Vector2f mouse = Input.mousePosition();
    Vector2f playerCamera = camera.getPosition();

    // Camera Origin
    float ox = playerCamera.x;
    float oy = playerCamera.y;

    // Camera scale
    float scale = camera.getScale();

    // Window size
    int w = IsoEngine.getWindow().getWidth() / 2;
    int h = IsoEngine.getWindow().getHeight() / 2;

    // Get local tile that mouse is (supposedly) in
    int sx = (int) Math.rint(((mouse.y - h + oy * scale) / (scale / 2) + (mouse.x - w - ox * scale) / scale) / 2);
    int sy = (int) Math.rint(((mouse.y - h + oy * scale) / (scale / 2) - (mouse.x - w - ox * scale) / scale) / 2);

    if (sx >= 0 && sy >= 0 && sx < map.getSize() && sy < map.getSize())
    {
      Vector2f screen = localToScreen(sx, sy, w, h, ox, oy, scale);
      float textureCoordinates = textureAtlas.getTextureCoordinates(map.getTile(sy, sx).getTexture()).x;
      int tileHeight = map.getTile(sy, sx).getHeight();
      Vector3i rgb = mouseMap.checkWhite(((mouse.x - screen.x) / scale / 2), ((mouse.y - screen.y) / scale / 2), textureCoordinates, tileHeight);

      int cc = 20; // Maximum counter to avoid infinite loops if gaps into the V O I D should occur
      while (cc >= 0)
      {
        if ((rgb.x == 0 && rgb.y == 0 && rgb.z == 0))
        {
          // Professionalism
          System.out.println("fuck");
        }

        if ((rgb.x == 255 && rgb.y == 255 && rgb.z == 255)) { break; }

        if (rgb.x == 255 && rgb.y == 0 && rgb.z == 255)
        {
          if (sx < map.getSize() - 1)
          {
            sx++;
          }
        }
        else if (rgb.x == 0 && rgb.y == 255 && rgb.z == 0)
        {
          if (sy < map.getSize() - 1)
          {
            sy++;
          }
        }
        else if (rgb.x == 0 && rgb.y == 255 && rgb.z == 255)
        {
          if (sx < map.getSize() - 1)
          {
            sx++;
          }
          if (sy < map.getSize() - 1)
          {
            sy++;
          }
        }

        textureCoordinates = textureAtlas.getTextureCoordinates(map.getTile(sy, sx).getTexture()).x;
        screen = localToScreen(sx, sy, w, h, ox, oy, scale);
        tileHeight = map.getTile(sy, sx).getHeight();

        rgb = mouseMap.checkWhite(((mouse.x - screen.x) / scale / 2), ((mouse.y - screen.y) / scale / 2), textureCoordinates, tileHeight);
        cc--;
      }

      int chunkX = chunk.getPosition().y * TileChunk.SIZE;
      int chunkY = chunk.getPosition().x * TileChunk.SIZE;

      if (sx >= chunkX && sy >= chunkY)
      {
        if (sx < chunkX + TileChunk.SIZE && sy < chunkY + TileChunk.SIZE)
        {
          tileShader.setUniform("mouse_position", sy % TileChunk.SIZE * TileChunk.SIZE + sx % TileChunk.SIZE);
          tileShader.bind();
        }
      }
    }
  }

  @Override
  public void render(Camera2D camera)
  {
    int w = IsoEngine.getWindow().getWidth() / 2;
    int h = IsoEngine.getWindow().getHeight() / 2;

    float scale = camera.getScale();

    Vector2f position = camera.getPosition();
    for (int i = 0; i < map.getChunks(); i++)
    {
      for (int j = 0; j < map.getChunks(); j++)
      {
        TileChunk chunk = map.getChunk(i, j);
        Vector2f pos = localToScreen(i * TileChunk.SIZE, j * TileChunk.SIZE, w, h, -position.x, position.y, scale);

        // No editing of terrain possible right now so no need to regenerate
        if (first)
        {
          TileChunkGenerator.generate(map, camera, tileShader, textureAtlas, chunk);
        }

        if (pos.x + pos.y / 2 > 0 - TileChunk.SIZE * scale - w && pos.x + pos.y / 2 < w * 2 + TileChunk.SIZE * scale + w)
        {
          if (pos.y > 0 - TileChunk.SIZE * scale && pos.y < h * 2 + TileChunk.SIZE * scale)
          {
            TileChunkGenerator.prepare(camera, tileShader, textureAtlas);
            setMouseUniform(camera, chunk);
            chunk.render();
          }
        }
      }
    }

    if (first)
    {
      first = false;
    }
  }

  public TileMapRenderer(TileMap map)
  {
    super();
    tileShader = new Shader("static");
    textureAtlas = new TextureAtlas("tileset0", 16, 16);
    this.map = map;
  }
}