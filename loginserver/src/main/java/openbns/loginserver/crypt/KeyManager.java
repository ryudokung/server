package openbns.loginserver.crypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 22.01.14
 * Time: 0:08
 */
public class KeyManager
{
  public static final byte[] ST_SERVER_KEY = { 64, -126, 22, -21, -80, -109, -40, 5 };
  public static final byte[] ND_SERVER_KEY = { -13, -13, -15, -32, 51, 6, -49, -43, -1, -66, 109, 71, 61, 28, 105, -91, 73, 67, 17, -42, -72, 46, -22, 127, 37, -81, 28, 15, -89, 100, -60, 15, -44, -52, -121, 52, 77, 84, -92, -73, -23, 127, -85, -44, 38, 93, -35, 25, -15, -84, 122, -40, 108, -82, -25, -31, -1, 124, 80, -2, 0, -30, -48, -124, -116, 56, 110, 44, 60, 94, 61, -109, 91, 72, -125, -104, 81, -11, 26, -22, 76, 119, 114, 45, -123, -128, 111, -109, -103, 89, -86, -29, 101, -54, -26, 0, -13, -6, 36, 99, 1, -87, 20, -72, 29, 11, -43, -64, -106, -119, -90, 36, -85, 23, -12, -119, 55, -74, -41, -8, -121, 33, 82, 2, 24, 55, -112, 96 };
  private static KeyManager ourInstance = new KeyManager();

  public static KeyManager getInstance()
  {
    return ourInstance;
  }

  private KeyManager()
  {
  }

  public byte[] getPasswordHash( String password ) throws NoSuchAlgorithmException
  {
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( password.getBytes() );
    return digest.digest();
  }
}
