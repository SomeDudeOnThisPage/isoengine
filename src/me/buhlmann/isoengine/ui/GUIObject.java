package me.buhlmann.isoengine.ui;

import me.buhlmann.isoengine.gfx.texture.GUITexture;

public abstract class GUIObject
{
  private GUITexture texture;
  public abstract void render();
}
