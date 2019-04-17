package me.buhlmann.isoengine.gamestate;

import me.buhlmann.isoengine.IsoEngine;
import me.buhlmann.isoengine.gfx.Camera2D;
import me.buhlmann.isoengine.gfx.render.TileMapRenderer;
import me.buhlmann.isoengine.input.Input;
import me.buhlmann.isoengine.level.TileMap;
import me.buhlmann.isoengine.player.Player;
import me.buhlmann.isoengine.ui.Label;
import me.buhlmann.isoengine.ui.text.TrueTypeFont;

import static org.lwjgl.glfw.GLFW.*;

public class GamestateGame extends Gamestate
{
  private TileMapRenderer renderer;
  private TileMap map;

  private long next_rotate = System.currentTimeMillis();

  private Player player;

  private Label label;
  private Label label2;
  private Label label3;

  private Label chunks;
  private Label tiles;

  public Player getPlayer()
  {
    return player;
  }

  @Override
  public void initialize()
  {
    int w = IsoEngine.getWindow().getWidth();
    int h = IsoEngine.getWindow().getHeight();

    player = Player.add("game_default");
    map = new TileMap(50);
    renderer = new TileMapRenderer(map);

    label = new Label("Allocated Memory: %%", new TrueTypeFont("monospaced", 32), 20, 20);
    label2 = new Label("Free Memory: %%", new TrueTypeFont("monospaced", 32), 20, 40);
    label3 = new Label("Used Memory: %%", new TrueTypeFont("monospaced", 32), 20, 60);

    chunks = new Label("Chunks rendered: %%", new TrueTypeFont("monospaced", 32), -275, 20);
    tiles = new Label( "Tiles rendered:  %%", new TrueTypeFont("monospaced", 32), -275, 40);

    player.getCamera().translate(500.0f, 500.0f);
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

  Runtime runtime = Runtime.getRuntime();

  @Override
  public void render()
  {
    label.setText( "Allocated Memory: " + runtime.totalMemory() / (1024 * 1024) + " mb");
    label2.setText("Free Memory:      " + runtime.freeMemory() / (1024 * 1024) + " mb");
    label3.setText("Used Memory:      " + (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024) + " mb");

    chunks.setText("#Chunks: " + renderer.getChunkAmount());
    tiles.setText( "#Tiles:  " + renderer.getChunkAmount() * 32 * 32);

    renderer.render(player.getCamera());
    label.render();
    label2.render();
    label3.render();

    chunks.render();
    tiles.render();
  }
}
