package me.buhlmann.isoengine.ui;

public abstract class GUIObject
{
  private static final float[] mesh = new float[]
  {
    -1.0f, -1.0f,     // Top
    1.0f, -1.0f,      // Right Corner
    1.0f,  1.0f,      // Bottom
    -1.0f,  1.0f,     // Left Corner
  };

  private static int[] indices = new int[]
  {
    0, 1, 2,
    2, 3, 0
  };

  public abstract void render();
}
