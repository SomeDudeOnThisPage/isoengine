package me.buhlmann.isoengine.gamestate;

public abstract class Gamestate
{
  public abstract void initialize();
  public abstract void input();
  public abstract void render();
}
