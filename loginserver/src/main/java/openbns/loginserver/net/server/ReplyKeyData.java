package openbns.loginserver.net.server;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 18.01.14
 * Time: 15:52
 */
@XStreamAlias( "Reply" )
public class ReplyKeyData
{
  @XStreamAlias( "KeyData" )
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
