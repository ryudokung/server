package openbns.loginserver.net.client.impl;

import io.netty.buffer.ByteBufInputStream;
import openbns.commons.xml.StsXStream;
import openbns.loginserver.net.client.AbstractRequestPacket;
import openbns.loginserver.net.client.dto.KeyDataDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:14
 */
public class RequestKeyData extends AbstractRequestPacket
{
  private static final Log log = LogFactory.getLog( RequestKeyData.class );

  @Override
  public void read()
  {
    StsXStream stream = new StsXStream();
    stream.processAnnotations( KeyDataDTO.class );

    KeyDataDTO connectDTO = (KeyDataDTO) stream.fromXML( new ByteBufInputStream( buf ) );
    log.debug( "Read from client object: " + connectDTO );
  }

  @Override
  public void execute()
  {

  }
}
