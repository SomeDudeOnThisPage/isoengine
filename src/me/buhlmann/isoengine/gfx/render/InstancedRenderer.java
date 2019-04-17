package me.buhlmann.isoengine.gfx.render;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import me.buhlmann.isoengine.gfx.Camera2D;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;

// TODO: Need chunks to be able to handle maps over the size of 400-ish
public abstract class InstancedRenderer implements Runnable
{
  private Thread thread;
  protected static final int MAX_INSTANCES = Short.MAX_VALUE * 32;
  private static final int DATA_LENGTH = 16;

  protected FloatBuffer buffer;

  protected int count;

  public abstract void render(Camera2D camera);

  public InstancedRenderer()
  {
    buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * DATA_LENGTH);

    thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {

  }
}
