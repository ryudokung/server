package openbns.commons.geometry;

/**
 * Created by: Eugene Chipachenko
 * Date: 01.03.14
 */
public interface IShape
{
  public boolean isInside( int x, int y );

  public boolean isInside( int x, int y, int z );

  public int getXmax();

  public int getXmin();

  public int getYmax();

  public int getYmin();

  public int getZmax();

  public int getZmin();
}
