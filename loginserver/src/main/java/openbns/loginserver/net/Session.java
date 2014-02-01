package openbns.loginserver.net;

import openbns.commons.util.CryptUtil;
import openbns.loginserver.crypt.KeyManager;
import openbns.loginserver.model.Account;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 16:29
 */
public class Session
{
  private static final SecureRandom rnd = new SecureRandom();
  
  private BigInteger privateKey;
  private BigInteger exchangeKey;

  private BigInteger serverExchangeKey;
  private BigInteger clientExchangeKey;

  private BigInteger sessionKey;
  private int sessionId;

  private Account account;

  public void init()
  {
    try
    {
      privateKey = KeyManager.getInstance().generatePrivateKey();
      exchangeKey = KeyManager.getInstance().generateExchangeKey( privateKey );
      sessionKey = new BigInteger( rnd.generateSeed( 8 ) );
    }
    catch( NoSuchAlgorithmException e )
    {
      e.printStackTrace();
    }
  }

  public BigInteger generateServerExchangeKey() throws NoSuchAlgorithmException, IOException
  {
    byte[] passwordHash = account.getPassword();

    BigInteger spKey = KeyManager.getInstance().generateAIIKey( CryptUtil.bigIntegerToByteArray( sessionKey ), passwordHash );
    BigInteger two = new BigInteger( "2" );
    BigInteger decKey = two.modPow( spKey, KeyManager.N );
    decKey = decKey.multiply( KeyManager.P ).mod( KeyManager.N );
    serverExchangeKey = exchangeKey.add( decKey ).mod( KeyManager.N );
    return serverExchangeKey;
  }

  public BigInteger generateClientExchangeKey( byte[] array ) throws NoSuchAlgorithmException, IOException
  {
    byte[] passwordHash = account.getPassword();

    BigInteger hash1 = KeyManager.getInstance().generateAIIKey( array, CryptUtil.bigIntegerToByteArray( serverExchangeKey ) );
    BigInteger hash2 = KeyManager.getInstance().generateAIIKey( CryptUtil.bigIntegerToByteArray( sessionKey ), passwordHash );

    BigInteger v27 = new BigInteger( CryptUtil.hexToString( array ), 16 );
    BigInteger v21 = exchangeKey.modPow( hash1.multiply( hash2 ), KeyManager.N ).multiply( v27.modPow( privateKey, KeyManager.N ) ).mod( KeyManager.N );

    return clientExchangeKey;
  }

  public void setAccount( Account account )
  {
    this.account = account;
  }

  public BigInteger getPrivateKey()
  {
    return privateKey;
  }

  public BigInteger getExchangeKey()
  {
    return exchangeKey;
  }

  public BigInteger getServerExchangeKey()
  {
    return serverExchangeKey;
  }

  public BigInteger getSessionKey()
  {
    return sessionKey;
  }

  public int getSessionId()
  {
    return sessionId;
  }

  public void setSessionId( int sessionId )
  {
    this.sessionId = sessionId;
  }

  public Account getAccount()
  {
    return account;
  }

  @Override
  public String toString()
  {
    return "Session{" +
            "privateKey=" + privateKey +
            ", exchangeKey=" + exchangeKey +
            ", sessionKey=" + sessionKey +
            ", sessionId=" + sessionId +
            '}';
  }
}
