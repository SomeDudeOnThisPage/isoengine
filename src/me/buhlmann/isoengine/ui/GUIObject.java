package me.buhlmann.isoengine.ui;

import me.buhlmann.isoengine.gfx.shader.Shader;
import me.buhlmann.isoengine.gfx.util.VertexArray;
import org.joml.Vector2f;

public abstract class GUIObject
{
  protected static Shader shader;

  protected VertexArray vao;
  protected Vector2f position;

  public abstract void render();

  public GUIObject(int x, int y)
  {
    position = new Vector2f(x, y);
  }
}
