package me.buhlmann.isoengine.level;

public class Tile
{
  private static final int FLAG_NORTH = 1;
  private static final int FLAG_EAST = 2;
  private static final int FLAG_SOUTH = 4;
  private static final int FLAG_WEST = 8;

  private int texture;
  private int drawHeight;
  private int height;
  private int adjacencies;

  public static int getNorth(TileMap map, int x, int y)
  {
    if (x > 0 && y > 0)
    {
      int height = Math.max(map.getTile(x - 1, y - 1).getHeight(), Math.max(map.getTile(x - 1, y).getHeight(), map.getTile(x, y - 1).getHeight()));

      return height;
    }

    return -1;
  }

  public static int getSouth(TileMap map, int x, int y)
  {
    if (x + 1 < map.getSize() && y + 1 < map.getSize())
    {
      int height = Math.max(map.getTile(x + 1, y + 1).getHeight(), Math.max(map.getTile(x + 1, y).getHeight(), map.getTile(x, y + 1).getHeight()));

      return height;
    }
    return -1;
  }

  public static int getEast(TileMap map, int x, int y)
  {
    if (x > 0 && y + 1 < map.getSize())
    {
      int height = Math.max(map.getTile(x - 1, y).getHeight(), Math.max(map.getTile(x - 1, y + 1).getHeight(), map.getTile(x, y + 1).getHeight()));

      return height;
    }
    return -1;
  }

  public static int getWest(TileMap map, int x, int y)
  {
    if (x + 1 < map.getSize() && y - 1 > 0)
    {
      int height = Math.max(map.getTile(x + 1, y).getHeight(), Math.max(map.getTile(x + 1, y - 1).getHeight(), map.getTile(x, y - 1).getHeight()));

      return height;
    }
    return -1;
  }

  public void setTexture(int texture)
  {
    this.texture = texture;
  }

  public void setHeight(int h, int dh)
  {
    this.height = h;
    this.drawHeight = dh;
  }

  public int getAdjacencies()
  {
    return adjacencies;
  }

  public void updateAdjacencies(TileMap map, int tx, int ty)
  {
    adjacencies = 0;

    Tile tile = map.getTile(tx, ty);
    int height = tile.height;

    int cx = tx;
    int cy = ty;

    // Check north tile
    if (getNorth(map, cx, cy) > height)
    {
      adjacencies = adjacencies | FLAG_NORTH;
    }

    if (getSouth(map, cx, cy) > height)
    {
      adjacencies = adjacencies | FLAG_SOUTH;
    }

    if (getEast(map, cx, cy) > height)
    {
      adjacencies = adjacencies | FLAG_EAST;
    }

    if (getWest(map, cx, cy) > height)
    {
      adjacencies = adjacencies | FLAG_WEST;
    }
  }

  public int getTexture()
  {
    return texture;
  }

  public int getHeight()
  {
    return height;
  }

  public int getDrawHeight()
  {
    return drawHeight;
  }

  public Tile(int texture, int drawHeight, int height)
  {
    this.texture = texture;
    this.drawHeight = drawHeight;
    this.height = height;

    adjacencies = 0;
  }
}
