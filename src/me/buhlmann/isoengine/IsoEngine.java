package me.buhlmann.isoengine;

import com.sun.management.OperatingSystemMXBean;
import me.buhlmann.isoengine.gamestate.GamestateGame;
import me.buhlmann.isoengine.gamestate.GamestateManager;
import me.buhlmann.isoengine.gfx.util.Window;
import me.buhlmann.isoengine.input.Input;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class IsoEngine
{
  public static final Window WINDOW_MANAGER = new Window();
  public static final GamestateManager GAME_MANAGER = new GamestateManager();

  public static Window getWindow()
  {
    return WINDOW_MANAGER;
  }

  private void loop()
  {
    Input.initialize();

    while (!WINDOW_MANAGER.shouldClose())
    {
      //System.out.println("Free Memory: " + Runtime.getRuntime().freeMemory() / 1024);

      GAME_MANAGER.input();
      GAME_MANAGER.render();
      WINDOW_MANAGER.update();

      //OperatingSystemMXBean b = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
      //System.out.println(Math.round(b.getProcessCpuLoad() * 100.0  * 100.0) / 100.0 + "%");

      try
      {
        Thread.sleep(1);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws IOException
  {
    GAME_MANAGER.setGamestate(new GamestateGame());
    new IsoEngine().loop();
  }
}
