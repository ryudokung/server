package openbns.loginserver.crypt;

import java.math.BigInteger;
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
  /**
   * Thanks to Luna9966
   */
  public static final BigInteger N = new BigInteger( "E306EBC02F1DC69F5B437683FE3851FD9AAA6E97F4CBD42FC06C72053CBCED68EC570E6666F529C58518CF7B299B5582495DB169ADF48ECEB6D65461B4D7C75DD1DA89601D5C498EE48BB950E2D8D5E0E0C692D613483B38D381EA9674DF74D67665259C4C31A29E0B3CFF7587617260E8C58FFA0AF8339CD68DB3ADB90AAFEE", 16 );
  public static final BigInteger P = new BigInteger( "7A39FF57BCBFAA521DCE9C7DEFAB520640AC493E1B6024B95A28390E8F05787D", 16 );
  public static final byte[] STATIC_KEY =
  {
          (byte) 0xAC, (byte)0x34, (byte)0xF3, (byte)0x07,
          (byte) 0x0D, (byte)0xC0, (byte)0xE5, (byte)0x23,
          (byte) 0x02, (byte)0xC2, (byte)0xE8, (byte)0xDA,
          (byte) 0x0E, (byte)0x3F, (byte)0x7B, (byte)0x3E,
          (byte) 0x63, (byte)0x22, (byte)0x36, (byte)0x97,
          (byte) 0x55, (byte)0x5D, (byte)0xF5, (byte)0x4E,
          (byte) 0x71, (byte)0x22, (byte)0xA1, (byte)0x4D,
          (byte) 0xBC, (byte)0x99, (byte)0xA3, (byte)0xE8
  };

  private static final String LOGIN_SUFFIX = "@plaync.co.kr";
  private static KeyManager ourInstance = new KeyManager();

  public static KeyManager getInstance()
  {
    return ourInstance;
  }

  private KeyManager()
  {
  }

  public byte[] getLoginHash( String login ) throws NoSuchAlgorithmException
  {
    login = login + LOGIN_SUFFIX;
    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( login.getBytes() );
    return digest.digest();
  }

  public byte[] getLoginPasswordHash( String login, String password ) throws NoSuchAlgorithmException
  {
    login = login + "@plaync.co.kr";
    String full = login + ":" + password;

    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( full.getBytes() );
    return digest.digest();
  }
}
