package openbns.loginserver.net.client.impl;

import openbns.commons.net.codec.sts.DefaultFullStsResponse;
import openbns.commons.net.codec.sts.StsResponseStatus;
import openbns.commons.net.codec.sts.StsVersion;
import openbns.loginserver.net.client.AbstractRequestHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:38
 */
public class RequestPing extends AbstractRequestHandler<Object>
{
  @Override
  public void execute()
  {
    channel.write( new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK ) );
  }
}
