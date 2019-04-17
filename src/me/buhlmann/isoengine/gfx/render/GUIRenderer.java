package me.buhlmann.isoengine.gfx.render;

import me.buhlmann.isoengine.gfx.shader.GUIShader;
import me.buhlmann.isoengine.ui.GUIObject;

import static org.lwjgl.opengl.GL30.*;

public class GUIRenderer
{
  private static final float[] vertices = new float[]
    {
      -1.0f,  1.0f,
      -1.0f, -1.0f,
       1.0f,  1.0f,
       1.0f, -1.0f,
    };

  private static final GUIObject[] guiObjects = new GUIObject[Short.MAX_VALUE];
  private int vao;

  public void render()
  {
    glBindVertexArray(vao);
    glEnableVertexAttribArray(GUIShader.ATTRIBUTE_POSITION);


    //glDisableVertexAttribArray(GUIShader.ATTRIBUTE_POSITION);
    glBindVertexArray(0);
  }

  public GUIRenderer()
  {
    // Initialize VAO
    vao = glGenVertexArrays();
    glBindVertexArray(vao);
    glVertexAttribPointer(GUIShader.ATTRIBUTE_POSITION, 2, GL_FLOAT, false, 0, 0L);
    glBindVertexArray(0);
  }
}
