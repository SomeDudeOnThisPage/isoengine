package me.buhlmann.isoengine.level.generation;

import me.buhlmann.isoengine.gfx.Camera2D;
import me.buhlmann.isoengine.gfx.shader.Shader;
import me.buhlmann.isoengine.gfx.texture.Tileset;
import me.buhlmann.isoengine.level.TileChunk;
import me.buhlmann.isoengine.level.TileMap;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class TileChunkGenerator
{
  private static float[] vertexData = new float[TileChunk.SIZE * TileChunk.SIZE * 3];
  private static float[] textureData = new float[TileChunk.SIZE * TileChunk.SIZE * 2];

  private static int r_pos_vertices = 0;
  private static int r_pos_textures = 0;

  public static void prepare(Camera2D camera, Shader tileShader, Tileset tileset)
  {
    vertexData = new float[TileChunk.SIZE * TileChunk.SIZE * 3];
    textureData = new float[TileChunk.SIZE * TileChunk.SIZE * 2];

    r_pos_vertices = 0;
    r_pos_textures = 0;

    Matrix4f cam = new Matrix4f().translate(new Vector3f(camera.getPosition().x, camera.getPosition().y, 0.0f));

    tileShader.setUniform("mouse_position", -1);
    tileShader.setUniform("cam_pos", cam);
    tileShader.setUniform("scale", camera.getScale());
    tileShader.setUniform("atlas_size", tileset.getRows());
    tileShader.setUniform("pr_matrix", camera.project());

    tileShader.bind();
    tileset.bind();
  }

  private static void fillVertical(int x, int y, int start, int finish, float scale, Vector2f tLoc)
  {
    for (int i = start; i < finish; i++)
    {
      updateVertexData(-(x - y) * scale, -(x + y) * 0.5f * scale + i * scale / 2.65f, 1.0f);
      updateTextureData(tLoc.x, tLoc.y);
    }
  }

  private static void updateVertexData(float v1, float v2, float v3)
  {
    vertexData[r_pos_vertices] = v1;
    vertexData[r_pos_vertices + 1] = v2;
    vertexData[r_pos_vertices + 2] = v3;

    r_pos_vertices += 3;
  }

  private static void updateTextureData(float t1, float t2)
  {
    textureData[r_pos_textures] = t1;
    textureData[r_pos_textures + 1] = t2;

    r_pos_textures += 2;
  }

  public static void generate(TileMap map, Camera2D camera, Shader tileShader, Tileset tileset, TileChunk chunk)
  {
    prepare(camera, tileShader, tileset);

    Vector2i position = chunk.getPosition();

    for (int x = 0; x < TileChunk.SIZE; x++)
    {
      for (int y = 0; y < TileChunk.SIZE; y++)
      {
        //System.out.println("Generating chunk[" + position.x + "][" + position.y + "] - Tile " + x + " " + y);

        int px = position.x * TileChunk.SIZE;
        int py = position.y * TileChunk.SIZE;

        // In order to determine what texture we need to use we need to check the heights of adjacent tiles and set the texture accordingly.
        Vector2f tLoc = tileset.getTextureCoordinates(map.getTile(px + x, py + y).getTexture());
        // Set the topmost tile to be rendered
        updateVertexData(-((x + px) - (y + py)), -((x + px) + (y + py)) * 0.5f + map.getTile(px + x, py + y).getDrawHeight() / 4.0f, 1.0f);
        updateTextureData(tLoc.x, tLoc.y);
      }
    }
    // Move the array to the GL_ARRAY_BUFFER
    chunk.update(vertexData, textureData);
  }

}
