package openbns.loginserver.net;

import openbns.loginserver.crypt.KeyManager;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 30.01.14
 * Time: 16:29
 */
public class Session
{
  private BigInteger privateKey;
  private BigInteger exchangeKey;
  private BigInteger sessionKey;

  public Session()
  {
    try
    {
      privateKey = KeyManager.getInstance().generatePrivateKey();
      exchangeKey = KeyManager.getInstance().generateExchangeKey( privateKey );
    }
    catch( NoSuchAlgorithmException e )
    {
      e.printStackTrace();
    }
  }
}
