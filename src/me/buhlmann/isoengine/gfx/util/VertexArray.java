package me.buhlmann.isoengine.gfx.util;

import static org.lwjgl.opengl.GL30.*;


import org.lwjgl.BufferUtils;
import java.nio.*;
import java.util.ArrayList;

public class VertexArray
{
  /**
   * Maximum number of attributes, ranging from 0 to MAX_ATTRIBUTES - 1.
   */
  protected static final int MAX_ATTRIBUTES = 8;

  /**
   * Currently enabled vertex array, so we do not accidentally enable the already bound vertex array again.
   */
  private static int current = 0;

  /**
   * Numerical ID of the vertex array.
   */
  private int id;

  /**
    Enabled attributes for this vertex array.
   */
  protected boolean[] attributes;

  /**
   * Stores the indices of the loaded VertexBufferObjects used by this VertexArray
   */
  private ArrayList<Integer> vbo_loaded = new ArrayList<>();

  /**
   * Numerical ID of the index buffer.<br>If indexing is not used, this will be -1.
   */
  protected int indices;

  /**
   * Bind the vertex array and enable all attribute arrays used.
   */
  public void bind()
  {
    // Check if the vertex array is already enabled.
    if (current != id)
    {
      glBindVertexArray(id);

      for (int i = 0; i < MAX_ATTRIBUTES; i++)
      {
        if (attributes[i])
        {
          glEnableVertexAttribArray(i);
        }
      }

      if (indices != -1)
      {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices);
      }

      current = id;
    }
  }

  /**
   * Unbind the vertex array and disable all attribute arrays used.
   */
  public void unbind()
  {
    // Do not unbind random vertex arrays, only unbind if we are sure it's the current one.
    if (current == id)
    {
      for (int i = 0; i < MAX_ATTRIBUTES; i++)
      {
        if (attributes[i])
        {
          glDisableVertexAttribArray(i);
        }
      }

      if (indices != -1)
      {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
      }

      glBindVertexArray(0);

      current = 0;
    }
  }

  /**
   * Creates an index buffer from the given arguments. This will switch the VAO to render with elements.
   * @param indices Indices
   */
  public void addIndices(int[] indices)
  {
    // Store initial data
    IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
    buffer.put(indices);
    buffer.flip();

    this.indices = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.indices);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
  }

  private int COUNT = 6; // Hardcoded for now. Should suffice anyway as the engine is 2D only and we basically only draw quads

  /**
   * Renders the VAO. Depending on whether an index buffer has been set, this will render using glDrawElements or glDrawArrays.
   */
  public void render()
  {
    bind();

    if (indices != -1)
    {
      glDrawElements(GL_TRIANGLES, COUNT, GL_UNSIGNED_INT, 0);
    }
    else
    {
      glDrawArrays(GL_TRIANGLES, 0, COUNT);
    }

    unbind();
  }

  /**
   * Creates a VertexBufferObject and binds it as a GL_STATIC DRAW attribute at the given position.
   */
  public void addAttribute(int index, int length, float[] init)
  {
    if (index < MAX_ATTRIBUTES)
    {
      bind();

      int vbo = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, vbo);

      // Store initial data
      FloatBuffer buffer = BufferUtils.createFloatBuffer(init.length);
      buffer.put(init);
      buffer.flip();

      glEnableVertexAttribArray(index);
      glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
      glVertexAttribPointer(index, length, GL_FLOAT, false, 0, 0);

      attributes[index] = true;
      vbo_loaded.add(vbo);

      unbind();
    }
  }

  /**
   * Creates a vertex array.
   */
  public VertexArray()
  {
    attributes = new boolean[MAX_ATTRIBUTES];
    id = glGenVertexArrays();
    indices = -1;
  }

  /**
   * Deprecated but will work for now.
   */
  /*@Override
  public void finalize()
  {
    glDeleteVertexArrays(id);
    for (Integer i : vbo_loaded)
    {
      glDeleteBuffers(i);
    }
  }*/
}
