package openbns.loginserver.service;

import openbns.commons.util.UUIDHelper;
import openbns.loginserver.crypt.HashHelper;
import openbns.loginserver.dao.AccountDAO;
import openbns.loginserver.model.AccessLevel;
import openbns.loginserver.model.Account;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 19:53
 */
public class UserRegistration
{
  private static final AccountDAO dao = AccountDAO.getInstance();
  private static UserRegistration ourInstance = new UserRegistration();

  public static UserRegistration getInstance()
  {
    return ourInstance;
  }

  private UserRegistration()
  {
  }

  public Account createAccount( String login, String password ) throws NoSuchAlgorithmException
  {
    Account account = new Account();
    account.setGuid( UUIDHelper.uuid() );
    account.setLogin( login );
    account.setPassword( HashHelper.passwordHash( login, password ) );
    account.setAccessLevel( AccessLevel.NORMAL );
    account.setLastLogin( new Date() );
    account.setLastIp( "127.0.0.1" );
    account.setLastServerId( 0 );
    return dao.insert( account );
  }
}
