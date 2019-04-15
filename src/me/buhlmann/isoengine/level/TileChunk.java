package me.buhlmann.isoengine.level;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.*;

import me.buhlmann.isoengine.gfx.util.IRVertexArray;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class TileChunk
{
  public static final int SIZE = 32;

  private Vector2i position;

  private Tile[][] tiles;

  private IRVertexArray vao;

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
    vao.update(vertexData, texturePositions);
  }

  public void render()
  {
    vao.bind();
    glDrawElementsInstanced(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0, SIZE * SIZE);
    vao.unbind();
  }

  public TileChunk(int px, int py)
  {
    vao = new IRVertexArray(SIZE * SIZE * 3);
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
