package openbns.commons.geometry;

/**
 * Created by: Eugene Chipachenko
 * Date: 01.03.14
 */
public class Point3D extends Point2D
{
  public static final Point3D[] EMPTY_ARRAY = new Point3D[ 0 ];
  public int z;

  public Point3D()
  {
  }

  public Point3D( int x, int y, int z )
  {
    super( x, y );
    this.z = z;
  }

  public int getZ()
  {
    return z;
  }

  @Override
  public boolean equals( Object o )
  {
    return o == this || o != null && o.getClass() == getClass() && equals( (Point3D) o );
  }

  public boolean equals( Point3D p )
  {
    return equals( p.x, p.y, p.z );
  }

  public boolean equals( int x, int y, int z )
  {
    return (this.x == x) && (this.y == y) && (this.z == z);
  }

  @Override
  public String toString()
  {
    return "[x: " + this.x + " y: " + this.y + " z: " + this.z + "]";
  }
}