package openbns.loginserver.net.client;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 17.01.14
 * Time: 23:03
 */
@XStreamAlias( "Request" )
public class RequestKeyData
{
  private String keyData;

  public String getKeyData()
  {
    return keyData;
  }

  public void setKeyData( String keyData )
  {
    this.keyData = keyData;
  }
}
