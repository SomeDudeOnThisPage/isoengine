package me.buhlmann.isoengine.input;

import static org.lwjgl.glfw.GLFW.*;

import me.buhlmann.isoengine.IsoEngine;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Input
{
  private static double scroll;
  private static Vector2f mouse_pos = new Vector2f();

  public static void initialize()
  {
    glfwSetScrollCallback(IsoEngine.getWindow().getWindow(), new GLFWScrollCallback()
    {
      @Override
      public void invoke(long window, double x, double y) {
        scroll = y;
      }
    });

    glfwSetCursorPosCallback(IsoEngine.getWindow().getWindow(), new GLFWCursorPosCallback() {
      @Override
      public void invoke(long window, double x, double y) {
        mouse_pos.set((float) x, (float) y);
      }
    });
  }

  public static boolean keyPressed(int key)
  {
    return glfwGetKey(IsoEngine.getWindow().getWindow(), key) == 1;
  }

  public static void resetMouseScroll()
  {
    scroll = 0;
  }

  public static double mouseScroll()
  {
    return scroll;
  }

  public static Vector2f mousePosition() { return mouse_pos; }
}
