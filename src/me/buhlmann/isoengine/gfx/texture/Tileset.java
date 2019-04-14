package me.buhlmann.isoengine.gfx.texture;

import org.joml.Vector2f;

public class Tileset extends Texture
{
  private int rows;
  private int columns;

  public int getRows()
  {
    return rows;
  }

  public Vector2f getTextureCoordinates(int texture)
  {
    return new Vector2f(texture % rows / (float) rows, (float) Math.floor(texture / (float) columns) / columns);
  }

  public Tileset(String name, int rows, int columns)
  {
    super(name);

    this.rows = rows;
    this.columns = columns;
  }
}
