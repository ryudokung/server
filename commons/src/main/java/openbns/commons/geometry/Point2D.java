package openbns.commons.geometry;

/**
 * Created by: Eugene Chipachenko
 * Date: 01.03.14
 */
public class Point2D
{
  public int x;
  public int y;

  public Point2D()
  {
  }

  public Point2D( int x, int y )
  {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals( Object o )
  {
    return o == this || o != null && o.getClass() == getClass() && equals( (Point2D) o );
  }

  public boolean equals( Point2D p )
  {
    return equals( p.x, p.y );
  }

  public boolean equals( int x, int y )
  {
    return (this.x == x) && (this.y == y);
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  @Override
  public String toString()
  {
    return "[x: " + this.x + " y: " + this.y + "]";
  }
}