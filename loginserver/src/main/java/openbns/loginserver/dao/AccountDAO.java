package openbns.loginserver.dao;

import openbns.DataBaseFactory;
import openbns.commons.db.DbUtils;
import openbns.loginserver.model.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 25.01.14
 * Time: 15:16
 */
public class AccountDAO
{
  private static final Log log = LogFactory.getLog( AccountDAO.class );
  private static final String INSERT_USER = "INSERT INTO accounts VALUES (?,?,?,?,?)";
  private static final String SELECT_BY_LOGIN = "SELECT * from accounts WHERE login = ?";

  private static AccountDAO ourInstance = new AccountDAO();

  public static AccountDAO getInstance()
  {
    return ourInstance;
  }

  private AccountDAO()
  {
  }

  public void insert( Account account )
  {
    Connection con = null;
    PreparedStatement statement = null;

    try
    {
      con = DataBaseFactory.getInstance().getConnection();
      statement = con.prepareStatement( INSERT_USER );

      statement.setString( 1, account.getLogin() );
      statement.setString( 2, account.getPassword() );
      statement.setTimestamp( 3, new Timestamp( account.getLastLogin().getTime() ) );
      statement.setInt( 4, account.getAccessLevel().ordinal() );
      statement.setString( 5, account.getLastIp() );

      statement.execute();
    }
    catch( SQLException e )
    {
      log.error( "Error inserting " + account, e );
    }
    finally
    {
      DbUtils.closeQuietly( con, statement );
    }
  }
}
