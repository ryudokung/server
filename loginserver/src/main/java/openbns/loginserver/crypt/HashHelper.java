package openbns.loginserver.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 19:41
 */
public class HashHelper
{
  private static final String LOGIN_SUFFIX = "@plaync.co.kr";

  public static byte[] loginHash( String login ) throws NoSuchAlgorithmException
  {
    login = login + LOGIN_SUFFIX;
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( login.getBytes() );
    return digest.digest();
  }

  public static byte[] passwordHash( String login, String password ) throws NoSuchAlgorithmException
  {
    String full_name = String.format( "%s%s:%s", login, LOGIN_SUFFIX, password );
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( full_name.getBytes() );
    return digest.digest();
  }
}
