package me.buhlmann.isoengine.gfx.texture;

public class FontAtlas extends TextureAtlas
{
  public FontAtlas(String name, int rows, int columns)
  {
    super("resources/fonts/" + name + ".png", rows, columns, true);
  }
}
