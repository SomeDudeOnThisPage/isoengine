package me.buhlmann.isoengine.player;

import me.buhlmann.isoengine.gfx.Camera2D;

import java.util.HashMap;

public class Player
{
  private static HashMap<String, Player> players = new HashMap<>();

  private String name;
  private Camera2D camera;

  public static HashMap<String, Player> getPlayers()
  {
    return players;
  }

  public static Player add(String name)
  {
    if (players.get(name) != null)
    {
      System.err.println("Tried to create a player who's name already exists.");
      return null;
    }

    Player player = new Player(name);
    players.put(name, player);

    return player;
  }

  public Camera2D getCamera()
  {
    return camera;
  }

  public Player(String name)
  {
    this.name = name;
    this.camera = new Camera2D();
  }
}
