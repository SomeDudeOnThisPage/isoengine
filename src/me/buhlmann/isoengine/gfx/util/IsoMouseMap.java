package me.buhlmann.isoengine.gfx.util;

import org.joml.Vector3i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IsoMouseMap
{
  BufferedImage mousemap;

  private int mm_scale = 64;

  public Vector3i checkWhite(float x, float y, float tex, int tileHeight)
  {
    // TODO: Actually find out why this is fuck. Simply catching an error and returning a white color is retarded...

    try
    {

      int offset_x = (int) Math.rint(x * mm_scale + mm_scale / 2.0f) + (int) (tex * 64 * 16);
      int offset_y = (int) Math.rint(y * mm_scale + mm_scale / 2.0f) + tileHeight * (mm_scale / 8);

      if (offset_x >= 0 && offset_y >= 0)
      {
        Color c = new Color(mousemap.getRGB(offset_x, offset_y));
        return new Vector3i(c.getRed(), c.getGreen(), c.getBlue());
      }

      return new Vector3i(255, 255, 255);

    }
    catch(Exception ignored)
    {
      return new Vector3i(255, 255, 255);
    }
  }

  public IsoMouseMap(String file)
  {

    try
    {
      mousemap = ImageIO.read(new File("resources/textures/" + file + ".png"));
    }
    catch (IOException e)
    {
      System.err.println(e.getMessage());
    }

  }
}
