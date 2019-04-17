package me.buhlmann.isoengine.gfx.util;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.system.MemoryUtil.NULL;

import me.buhlmann.isoengine.player.Player;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GLCapabilities;

public class Window
{
  private long window;
  private int width = 800;
  private int height = 600;

  private GLCapabilities capabilities;

  public long getWindow()
  {
    return window;
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }

  public boolean shouldClose()
  {
    return glfwWindowShouldClose(window);
  }

  public void update()
  {
    glfwPollEvents();
    glfwSwapBuffers(window);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  public Window()
  {
    if (!glfwInit())
    {
      System.err.println("[ERROR] [FATAL] Could not initialize GLFW.");
      System.exit(1);
    }

    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    window = glfwCreateWindow(width, height, "OpenGL Testing", NULL, NULL);

    glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback()
    {
      @Override
      public void invoke(long window, int w, int h){
        width = w;
        height = h;

        glfwSetWindowSize(window, w, h);
        glViewport(0, 0, w, h);

        Player.getPlayers().forEach((k,v) -> v.getCamera().updateMatrices(w, h));
      }
    });

    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    capabilities = createCapabilities();

    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }
}
