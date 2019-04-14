package me.buhlmann.isoengine.level;

import me.buhlmann.isoengine.level.generation.PerlinNoise;

public class TileMap
{
  private static final int FLAT = 0;
  private static final int SOUTH = 1;
  private static final int WEST = 2;
  private static final int SOUTHWEST = 3;
  private static final int NORTH = 4;
  private static final int LLL = 5;
  private static final int NORTHWEST = 6;
  private static final int SOUTHUP = 11;

  private TileChunk[][] map;
  private int size;

  public TileChunk getChunk(int x, int y)
  {
    return map[x][y];
  }

  public Tile getTile(int x, int y)
  {
    int chunkX = x / TileChunk.SIZE;
    int chunkY = y / TileChunk.SIZE;

    int tileX = x % TileChunk.SIZE;
    int tileY = y % TileChunk.SIZE;

    return map[chunkX][chunkY].getTile(tileX, tileY);
  }

  public int getSize()
  {
    return size * TileChunk.SIZE;
  }

  public int getChunks()
  {
    return size;
  }

  private void generate()
  {
    double nSize = 500.0;

    int octaves = 10;

    int tex;
    int water_level = 2;

    int a = 0;

    for (int i = 0; i < size * TileChunk.SIZE; i++)
    {
      for (int j = 0; j < size * TileChunk.SIZE; j++)
      {
        a++;

        double nHeight = 0;

        double frequency = 0.1;
        double amplitude = 0.7;
        double persistance = 0.92;
        double lacunarity = 1.95;

        for (int k = 0; k < octaves; k++)
        {
          nHeight += PerlinNoise.noise(i / nSize * frequency, j / nSize * frequency, frequency) * amplitude;

          amplitude *= persistance;
          frequency *= lacunarity;
        }
        nHeight = Math.abs(Math.floor(nHeight * 10.0));

        // Set water tiles accordingly
        if (nHeight <= water_level)
        {
          nHeight = 0;
          tex = 16;
        }
        else
        {
          nHeight = nHeight - water_level;
          tex = 0;
        }

        getTile(i, j).setTexture(tex);
        getTile(i, j).setHeight((int) nHeight, (int) nHeight);
      }
    }

    // Update tiles
    for (int i = 0; i < size * TileChunk.SIZE; i++)
    {
      for (int j = 0; j < size * TileChunk.SIZE; j++)
      {
        int row_offset = 0;

        Tile tile = getTile(i, j);

        if (tile.getTexture() >= 16)
        {
          row_offset++;
        }
        tile.updateAdjacencies(this, i, j);
        tile.setTexture(tile.getAdjacencies() + row_offset * 16);

      }
    }
  }

  public TileMap(int size)
  {
    this.size = size;

    map = new TileChunk[size][size];

    for (int x = 0; x < size; x++)
    {
      for (int y = 0; y < size; y++)
      {
        map[x][y] = new TileChunk(x, y);
      }
    }

    generate();
  }
}
