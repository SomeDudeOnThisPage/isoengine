package me.buhlmann.isoengine.gfx.render;

public class ChunkRenderer
{
}
    /*int size = map.getSize();

    Vector2f playerCamera = camera.getPosition();

    // Camera Origin
    float ox = playerCamera.x;
    float oy = playerCamera.y;

    // Window size
    int w = IsoEngine.getWindow().getWidth() / 2;
    int h = IsoEngine.getWindow().getHeight() / 2;

    prepare(camera);

    // Setup variables for map rendering
    // Tile size (height is scale / 2)
    float SCALE = camera.getScale();

      //If the mouse is on top of the current tile, set the uniform in the shader to the current tiles ID

    Vector2f mLoc = Input.mousePosition();
    tileShader.setUniform("mouse_position", -1);
    tileShader.bind();

    // Tile coordinates for top left corner -> Start drawing x-axis from there + 1
    double top_left = - ((h * 2 - h - oy * SCALE) / (SCALE / 2) + (w * 2 - w - ox * SCALE) / SCALE) / 2;

    // Tile coordinates for top right corner -> Start drawing y-axis from there + 1
    double top_right = - ((- oy * SCALE) / (SCALE / 2) - (-ox * SCALE) / SCALE) / 2 - w / SCALE - SCALE;

    if (top_left < 0) { top_left = 0; }
    if (top_right < 0) { top_right = 0; }

    for (int x = (int) Math.floor(top_left); x < size; x++)
    {
      for (int y = (int) Math.floor(top_right); y < size; y++)
      {
        float cx = -(x - y) * SCALE + ox * SCALE;
        float cy = -(x + y) * 0.5f * SCALE + oy * SCALE;

        if (cx >= -w - SCALE && cx < w + SCALE)
        {
          if (cy <= h + SCALE && cy >= -h - SCALE - map.map[x][y].getDrawHeight() * SCALE / 2)
          {

            // In order to determine what texture we need to use we need to check the heights of adjacent tiles
            // and set the texture accordingly.
            Vector2f tLoc = tileset.getTextureCoordinates(map.map[x][y].getTexture());

            // Check if we are on the edge of the map and elevated. If so, draw tiles to fill gaps.
            if (x == map.getSize() - 1 || y == map.getSize() - 1)
            {
              if (map.map[x][y].getDrawHeight() > 0)
              {
                fillVertical(x, y, 0, map.map[x][y].getDrawHeight(), SCALE, tLoc);
              }
            }
            else
            {
              // Establish heights of surrounding tiles
              int current_height = map.map[x][y].getDrawHeight();
              int east_height = map.map[x][y + 1].getDrawHeight();
              int south_height = map.map[x + 1][y].getDrawHeight();

              // Check if all adjacent tiles are covered and invisible. If not, render a tile below
              if (current_height > 0 && (east_height < current_height || south_height < current_height))
              {
                fillVertical(x, y, Math.min(east_height, south_height), map.map[x][y].getDrawHeight(), SCALE, tLoc);
              }
            }

            // Set the topmost tile to be rendered
            updateVertexData(-(x - y) * SCALE, -(x + y) * 0.5f * SCALE + map.map[x][y].getDrawHeight() * SCALE / 2.65f, 1.0f);
            updateTextureData(tLoc.x, tLoc.y);

            // Calculate local mouse coordinates in isometric space with camera offset
            // Note to self: Comment complex formulas or else when you look at it later it is fuck.
            float th = map.map[x][y].getHeight() * SCALE / 2.65f;
            double lx = ((mLoc.y - h + oy * SCALE + th) / (SCALE / 2) + (mLoc.x - w - ox * SCALE) / SCALE) / 2 + 0.2;
            double ly = ((mLoc.y - h + oy * SCALE + th) / (SCALE / 2) - (mLoc.x - w - ox * SCALE) / SCALE) / 2 + 0.2;

            // If current tile is being moused over set the uniform
            if (x == Math.rint(ly) && y == Math.rint(lx))
            {
              tileShader.setUniform("mouse_position", (r_pos_vertices - 3.0f) / 3);
              tileShader.bind();
            }
          }
        }
      }
    }
    // Move the array to the GL_ARRAY_BUFFER
    update(vertexData, textureData);
*/
//vao.bind();
//glDrawElementsInstanced(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0, 32 * 32 * 3);
//vao.unbind();

//finish();