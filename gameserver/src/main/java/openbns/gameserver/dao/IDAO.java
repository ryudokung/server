package openbns.gameserver.dao;

/**
 * Created by: Eugene Chipachenko
 * Date: 01.03.14
 */
public interface IDAO<T>
{
  void save( T t );

  void update( T t );

  void delete( T t );
}
