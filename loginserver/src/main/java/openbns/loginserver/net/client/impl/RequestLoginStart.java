package openbns.loginserver.net.client.impl;

import openbns.loginserver.net.client.AbstractRequestHandler;
import openbns.loginserver.net.client.dto.KeyDataDTO;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:11
 */
public class RequestLoginStart extends AbstractRequestHandler<KeyDataDTO>
{
  private static final String AUTH_LOGIN_START = "/Auth/LoginStart";

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
