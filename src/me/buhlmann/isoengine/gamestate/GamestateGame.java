package me.buhlmann.isoengine.gamestate;

import me.buhlmann.isoengine.gfx.Camera2D;
import me.buhlmann.isoengine.gfx.render.TileMapRenderer;
import me.buhlmann.isoengine.input.Input;
import me.buhlmann.isoengine.level.TileMap;
import me.buhlmann.isoengine.player.Player;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

public class GamestateGame extends Gamestate
{
  private TileMapRenderer renderer;
  private TileMap map;

  private long next_rotate = System.currentTimeMillis();

  private Player player;

  @Override
  public void initialize()
  {
    player = Player.add("game_default");

    map = new TileMap(2);
    renderer = new TileMapRenderer(map);
  }

  @Override
  public void input()
  {
    Camera2D playerCamera = player.getCamera();
    float scroll = playerCamera.getScrollSpeed();

    if (Input.keyPressed(GLFW_KEY_A))
    {
      playerCamera.translate(1 * scroll,0);
    }
    else if (Input.keyPressed(GLFW_KEY_D))
    {
      playerCamera.translate(-1 * scroll,0);
    }

    if (Input.keyPressed(GLFW_KEY_W))
    {
      playerCamera.translate(0,-1 * scroll);
    }
    else if (Input.keyPressed(GLFW_KEY_S))
    {
      playerCamera.translate(0,1 * scroll);
    }

    if (Input.keyPressed(GLFW_KEY_Q))
    {
      if (System.currentTimeMillis() > next_rotate)
      {
        next_rotate = System.currentTimeMillis() + 250;
      }
    }

    if (Input.keyPressed(GLFW_KEY_E))
    {
      if (System.currentTimeMillis() > next_rotate)
      {
        next_rotate = System.currentTimeMillis() + 250;
      }
    }

    if (Input.mouseScroll() > 0.0 && playerCamera.getScale() < 128)
    {
      playerCamera.modScale(4);
      Input.resetMouseScroll();
    }

    if (Input.mouseScroll() < 0.0 && playerCamera.getScale() > 4)
    {
      playerCamera.modScale(-4);
      Input.resetMouseScroll();
    }
  }

  @Override
  public void render()
  {
    renderer.render(player.getCamera());
  }
}
