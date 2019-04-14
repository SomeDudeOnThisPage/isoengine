package me.buhlmann.isoengine.gfx;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera2D
{
  private float x;
  private float y;

  private float scale;

  private float scrollSpeed;

  private Matrix4f projection;

  public void modScale(float mod)
  {
    scale += mod;
  }

  public void updateMatrices(int width, int height)
  {
    projection = new Matrix4f().ortho2D(-width / 2.0f, width / 2.0f, -height / 2.0f, height / 2.0f);
  }

  public Matrix4f project()
  {
    return projection;
  }

  public float getScale()
  {
    return scale;
  }

  public float getScrollSpeed()
  {
    return scrollSpeed;
  }

  public Vector2f getPosition()
  {
    return new Vector2f(x, y);
  }

  public void translate(float x, float y)
  {
    this.x += x;
    this.y += y;
  }

  public Camera2D()
  {
    x = 0;
    y = 0;

    scale = 64;
    scrollSpeed = 2.0f;

    projection = new Matrix4f().ortho2D(-400, 400, -300, 300);
  }
}
