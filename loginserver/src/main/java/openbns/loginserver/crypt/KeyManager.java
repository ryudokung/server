package openbns.loginserver.crypt;

import java.io.*;
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
  public static final byte[] STATIC_KEY = { (byte) 0xAC, (byte) 0x34, (byte) 0xF3, (byte) 0x07, (byte) 0x0D, (byte) 0xC0, (byte) 0xE5, (byte) 0x23, (byte) 0x02, (byte) 0xC2, (byte) 0xE8, (byte) 0xDA, (byte) 0x0E, (byte) 0x3F, (byte) 0x7B, (byte) 0x3E, (byte) 0x63, (byte) 0x22, (byte) 0x36, (byte) 0x97, (byte) 0x55, (byte) 0x5D, (byte) 0xF5, (byte) 0x4E, (byte) 0x71, (byte) 0x22, (byte) 0xA1, (byte) 0x4D, (byte) 0xBC, (byte) 0x99, (byte) 0xA3, (byte) 0xE8 };

  private static KeyManager ourInstance = new KeyManager();

  public static KeyManager getInstance()
  {
    return ourInstance;
  }

  private KeyManager()
  {
  }

  public BigInteger generatePrivateKey() throws NoSuchAlgorithmException
  {
    long time = System.currentTimeMillis();
    long ticks = 621355968000000000L + time * 10000;
    String s_time = String.valueOf( ticks );
    byte[] b_time = s_time.getBytes();

    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( b_time );
    return new BigInteger( digest.digest() ).abs();
  }

  public BigInteger generateExchangeKey( BigInteger privateKey )
  {
    BigInteger k = new BigInteger( "2" );
    return k.modPow( privateKey, N );
  }

  public BigInteger generateSharedKey( byte[] tmp1, byte[] tmp2 ) throws NoSuchAlgorithmException, IOException
  {
    byte[] sharedArray = new byte[ tmp1.length + tmp2.length ];

    System.arraycopy( tmp1, 0, sharedArray, 0, tmp1.length );
    System.arraycopy( tmp2, 0, sharedArray, tmp1.length, tmp2.length );

    MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
    digest.update( sharedArray );

    byte[] hash = digest.digest();
    byte[] reversed = reverseIntegerArray( hash );

    return new BigInteger( reversed );
  }

  private byte[] reverseIntegerArray( byte[] array ) throws IOException
  {
    ByteArrayOutputStream bos = new ByteArrayOutputStream( array.length );
    DataOutputStream dos = new DataOutputStream( bos );
    DataInputStream dis = new DataInputStream( new ByteArrayInputStream( array ) );

    while( dis.available() > 0 )
    {
      int i = dis.readInt();
      dos.writeInt( Integer.reverseBytes( i ) );
    }

    dis.close();
    dos.close();
    bos.close();

    return bos.toByteArray();
  }
}
