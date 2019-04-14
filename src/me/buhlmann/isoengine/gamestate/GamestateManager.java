package me.buhlmann.isoengine.gamestate;

public class GamestateManager
{
  private static Gamestate activeGamestate;

  public Gamestate getActiveGamestate()
  {
    return activeGamestate;
  }

  public void setGamestate(Gamestate gamestate)
  {
    if (gamestate != activeGamestate)
    {
      activeGamestate = gamestate;
      gamestate.initialize();
    }
  }

  public void render()
  {
    activeGamestate.render();
  }

  public void input()
  {
    activeGamestate.input();
  }
}
