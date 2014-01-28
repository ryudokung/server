package openbns.loginserver.net.client.impl;

import io.netty.buffer.ByteBufInputStream;
import openbns.commons.xml.StsXStream;
import openbns.loginserver.net.client.AbstractRequestPacket;
import openbns.loginserver.net.client.dto.LoginStartDTO;
import openbns.loginserver.net.server.dto.ReplyKeyData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:11
 */
public class RequestLoginStart extends AbstractRequestPacket
{
  private static final Log log = LogFactory.getLog( RequestLoginStart.class );

  @Override
  public void read()
  {
    StsXStream stream = new StsXStream();
    stream.processAnnotations( LoginStartDTO.class );

    LoginStartDTO connectDTO = (LoginStartDTO) stream.fromXML( new ByteBufInputStream( buf ) );
    log.debug( "Read from client object: " + connectDTO );
  }

  @Override
  public void execute()
  {
//    ReplyKeyData replyKeyData = new ReplyKeyData();
//    replyKeyData.setKeyData( "CAAAAECCFuuwk9gFgAAAAPPz8eAzBs/V/75tRz0caaVJQxHWuC7qfyWvHA+nZMQP1MyHNE1UpLfpf6vUJl3dGfGsethsrufh/3xQ/gDi0ISMOG4sPF49k1tIg5hR9RrqTHdyLYWAb5OZWarjZcrmAPP6JGMBqRS4HQvVwJaJpiSrF/SJN7bX+IchUgIYN5Bg" );
//
//    StsXStream stream = new StsXStream();
//    stream.processAnnotations( ReplyKeyData.class );
//    byte[] b = stream.toXML( replyKeyData ).getBytes();
//
//    DefaultStsResponse resp = new DefaultStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK );
//    resp.headers().add( StsHeaders.Names.CONTENT_LENGTH, b.length );
//    resp.headers().add( "s", session + "R" );
//
//    channel.write( resp );
//    channel.write( new DefaultLastStsContent( Unpooled.wrappedBuffer( b ) ) );
  }
}
