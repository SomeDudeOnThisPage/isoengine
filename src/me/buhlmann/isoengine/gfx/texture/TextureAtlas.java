package me.buhlmann.isoengine.gfx.texture;

import org.joml.Vector2f;

public class TextureAtlas extends Texture
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

  public TextureAtlas(String name, int rows, int columns, int filter, boolean fOverride)
  {
    super(name, filter, fOverride);

    this.rows = rows;
    this.columns = columns;
  }

  public TextureAtlas(String name, int rows, int columns, boolean fOverride)
  {
    super(name, fOverride);

    this.rows = rows;
    this.columns = columns;
  }

  public TextureAtlas(String name, int rows, int columns)
  {
    super(name);

    this.rows = rows;
    this.columns = columns;
  }
}
