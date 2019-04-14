package me.buhlmann.isoengine.gfx.texture;

import me.buhlmann.isoengine.gfx.util.GFXUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
  private static int current = 0;

  private int ID;

  public void bind()
  {
    if (current != ID)
    {
      glBindTexture(GL_TEXTURE_2D, ID);
      current = ID;
    }
  }

  public void unbind()
  {
    glBindTexture(GL_TEXTURE_2D, 0);
    current = 0;
  }

  public Texture(String name)
  {
    try
    {
      IntBuffer width = BufferUtils.createIntBuffer(1);
      IntBuffer height = BufferUtils.createIntBuffer(1);
      IntBuffer components = BufferUtils.createIntBuffer(1);

      ByteBuffer data = STBImage.stbi_load_from_memory(GFXUtil.ioLoadResource("resources/textures/" + name + ".png"), width, height, components, 4);

      ID = glGenTextures();
      glBindTexture(GL_TEXTURE_2D, ID);

      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

      // Cleanup
      glBindTexture(GL_TEXTURE_2D, 0);
      if (data != null)
        stbi_image_free(data);
    }
    catch (IOException e)
    {
      System.err.println("Could not load texture: resources/textures/" + name + ".png");
    }
  }
}
