package openbns.loginserver.net.client.impl;

import io.netty.buffer.ByteBufInputStream;
import openbns.commons.net.codec.sts.DefaultFullStsResponse;
import openbns.commons.net.codec.sts.StsResponseStatus;
import openbns.commons.net.codec.sts.StsVersion;
import openbns.commons.xml.StsXStream;
import openbns.loginserver.net.client.AbstractRequestPacket;
import openbns.loginserver.net.client.dto.ConnectDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:09
 */
public class RequestConnect extends AbstractRequestPacket
{
  private static final Log log = LogFactory.getLog( RequestConnect.class );

  @Override
  public void read()
  {
    StsXStream stream = new StsXStream();
    stream.processAnnotations( ConnectDTO.class );

    ConnectDTO connectDTO = (ConnectDTO) stream.fromXML( new ByteBufInputStream( buf ) );
    log.debug( "Read from client object: " + connectDTO );
  }

  @Override
  public void execute()
  {
    channel.write( new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK ) );
  }
}
