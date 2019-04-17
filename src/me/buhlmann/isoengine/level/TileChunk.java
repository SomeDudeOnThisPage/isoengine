package me.buhlmann.isoengine.level;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.*;

import me.buhlmann.isoengine.gfx.util.InstancedRenderingVertexArray;
import org.joml.Vector2i;

public class TileChunk
{
  public static final int SIZE = 32;

  private Vector2i position;

  private Tile[][] tiles;

  private InstancedRenderingVertexArray vao;
  public Vector2i getPosition()
  {
    return position;
  }

  public Tile getTile(int x, int y)
  {
    return tiles[x][y];
  }

  public void setTile(int x, int y, int texture)
  {
    tiles[x][y].setTexture(texture);
  }

  public void update(float[] vertexData, float[] texturePositions)
  {
    vao.updateInstancedAttributeData(2, vertexData);
    vao.updateInstancedAttributeData(3, texturePositions);
  }

  public void render()
  {
    vao.bind();
    glDrawElementsInstanced(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0, SIZE * SIZE);
    vao.unbind();
  }

  public TileChunk(int px, int py)
  {
    vao = new InstancedRenderingVertexArray();
    vao.addAttribute(0, 2, new float[] {-1.0f, -1.0f, 1.0f, -1.0f, 1.0f,  1.0f, -1.0f, 1.0f});
    vao.addIndices(new int[] {0, 1, 2, 2, 3, 0});
    vao.addAttribute(1, 2, new float[] {0.0f,  1.0f, 1.0f,  1.0f, 1.0f,  0.0f, 0.0f,  0.0f});
    vao.addInstancedAttribute(2, Short.MAX_VALUE, 3, 1);
    vao.addInstancedAttribute(3, Short.MAX_VALUE, 2, 1);

    tiles = new Tile[SIZE][SIZE];

    position = new Vector2i(px, py);

    for (int x = 0; x < SIZE; x++)
    {
      for (int y = 0; y < SIZE; y++)
      {
        tiles[x][y] = new Tile(0, 0, 0);
      }
    }
  }
}
