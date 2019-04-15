package me.buhlmann.isoengine.ui;

import me.buhlmann.isoengine.gfx.Camera2D;
import me.buhlmann.isoengine.gfx.util.IRVertexArray;

public abstract class GUIObject
{
  private static final float[] vertices = new float[]
  {
    -1.0f,  1.0f,     // Top
    -1.0f, -1.0f,      // Right Corner
     1.0f,  1.0f,      // Bottom
     1.0f, -1.0f,     // Left Corner
  };

  private static int[] indices = new int[]
  {
    0, 1, 2,
    2, 3, 0
  };

  protected IRVertexArray vao;

  public abstract void render(Camera2D camera);

  public static void initialize()
  {

  }

  public GUIObject()
  {
    vao = new IRVertexArray(Short.MAX_VALUE);
  }
}
