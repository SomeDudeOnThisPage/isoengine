package me.buhlmann.isoengine.gfx.render;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import me.buhlmann.isoengine.gfx.util.IRVertexArray;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;

// TODO: Need chunks to be able to handle maps over the size of 400-ish
public class InstancedRenderer implements Runnable
{
  private Thread thread;
  protected static final int MAX_INSTANCES = Short.MAX_VALUE * 32;
  private static final int DATA_LENGTH = 16;

  protected IRVertexArray vao;
  protected FloatBuffer buffer;

  protected int count;

  public void render()
  {
    vao.bind();
    glDrawElementsInstanced(GL_TRIANGLE_STRIP, 6, GL_UNSIGNED_INT, 0, count);
    vao.unbind();
  }

  public void update(float[] vertexData, float[] texturePositions)
  {
    count = vertexData.length / 3;

    // Repopulate the buffer with our new vertex data
    buffer.put(vertexData);
    buffer.flip();

    // Bind the vertex vbo and populate it with sub-data
    glBindBuffer(GL_ARRAY_BUFFER, vao.getModelViewMatrixVBO());
    glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);

    // Repopulate buffer with texture atlas position data
    buffer.clear();
    buffer.put(texturePositions);
    buffer.flip();

    // Bind the vertex vbo and populate it with sub-data
    glBindBuffer(GL_ARRAY_BUFFER, vao.getTextureAtlasPositionVBO());
    glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);

    // Cleanup
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    buffer.clear();
  }

  public InstancedRenderer()
  {
    vao = new IRVertexArray(MAX_INSTANCES);
    buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * DATA_LENGTH);

    thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {

  }
}
