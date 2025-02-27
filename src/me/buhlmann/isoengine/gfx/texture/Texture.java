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
    this("resources/textures/" + name + ".png", false);
  }
  public Texture(String name, boolean fOverride) { this(name, GL_NEAREST, false); }

  public Texture(String name, int filter, boolean fOverride)
  {
    try
    {
      IntBuffer width = BufferUtils.createIntBuffer(1);
      IntBuffer height = BufferUtils.createIntBuffer(1);
      IntBuffer components = BufferUtils.createIntBuffer(1);

      // STB is a blessing
      ByteBuffer data = STBImage.stbi_load_from_memory(GFXUtil.ioLoadResource(name), width, height, components, 4);

      ID = glGenTextures();
      glBindTexture(GL_TEXTURE_2D, ID);

      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

      // Cleanup
      glBindTexture(GL_TEXTURE_2D, 0);
      if (data != null)
        stbi_image_free(data);
    }
    catch (IOException e)
    {
      System.err.println("Could not load texture " + name + ": " + e + " " + e.getMessage());
    }
  }
}
