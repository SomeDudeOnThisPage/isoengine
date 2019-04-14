package me.buhlmann.isoengine.gfx.util;

import me.buhlmann.isoengine.gfx.shader.Shader;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.*;

public class IRVertexArray
{
  private static float[] vertices = new float[]
    {
      -1.0f, -1.0f,    // Top
      1.0f, -1.0f,    // Right Corner
      1.0f,  1.0f,    // Bottom
      -1.0f,  1.0f,    // Left Corner
    };

  private static int[] indices = new int[]
    {
      0, 1, 2,
      2, 3, 0
    };

  private static float[] texture_coordinates = new float[]
    {
      0.0f,  1.0f,
      1.0f,  1.0f,
      1.0f,  0.0f,
      0.0f,  0.0f,
    };

  private int vaoID;
  private int mvmID;
  private int tapID;
  private int indID;

  public int getTextureAtlasPositionVBO()
  {
    return tapID;
  }

  public int getModelViewMatrixVBO()
  {
    return mvmID;
  }

  public void bind()
  {
    glBindVertexArray(vaoID);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indID);
  }

  public void unbind()
  {
    glBindVertexArray(0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
  }

  public IRVertexArray(int max)
  {
    vaoID = glGenVertexArrays();
    glBindVertexArray(vaoID);

    // Vertex Positions
    FloatBuffer fb = BufferUtils.createFloatBuffer(2 * 6);
    fb.put(vertices);
    fb.flip();

    int pvbo = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, pvbo);
    glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);

    glVertexAttribPointer(Shader.ATTRIBUTE_VERTICES, 2, GL_FLOAT, false, 0, 0L);
    glEnableVertexAttribArray(Shader.ATTRIBUTE_VERTICES);

    // Indices
    IntBuffer ib = BufferUtils.createIntBuffer(6);
    ib.put(indices);
    ib.flip();

    indID = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib, GL_STATIC_DRAW);

    // Model view matrix vbo
    mvmID = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, mvmID);
    glBufferData(GL_ARRAY_BUFFER, max * 4, GL_STREAM_DRAW);

    glEnableVertexAttribArray(Shader.ATTRIBUTE_WORLDPOS);
    glVertexAttribPointer(Shader.ATTRIBUTE_WORLDPOS, 3, GL_FLOAT, false, 3 * 4, 0L);
    glVertexAttribDivisor(Shader.ATTRIBUTE_WORLDPOS, 1);

    // Texture Atlas position vbo
    tapID = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, tapID);
    glBufferData(GL_ARRAY_BUFFER, max * 4, GL_STREAM_DRAW);

    glEnableVertexAttribArray(Shader.ATTRIBUTE_TEXTURE_POSITION);
    glVertexAttribPointer(Shader.ATTRIBUTE_TEXTURE_POSITION, 2, GL_FLOAT, false, 2 * 4, 0L);
    glVertexAttribDivisor(Shader.ATTRIBUTE_TEXTURE_POSITION, 1);

    // Texture coordinates
    fb = BufferUtils.createFloatBuffer(2 * 6);
    fb.put(texture_coordinates);
    fb.flip();

    int tvbo = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, tvbo);
    glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);

    glVertexAttribPointer(Shader.ATTRIBUTE_TEXTURES, 2, GL_FLOAT, false, 0, 0L);
    glEnableVertexAttribArray(Shader.ATTRIBUTE_TEXTURES);
  }
}
