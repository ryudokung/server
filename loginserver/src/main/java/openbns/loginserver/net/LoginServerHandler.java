package openbns.loginserver.net;

import com.thoughtworks.xstream.XStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import openbns.commons.net.codec.sts.*;
import openbns.loginserver.net.server.ReplyKeyData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 16.01.14
 * Time: 21:03
 */
public class LoginServerHandler extends ChannelInboundHandlerAdapter
{
  private static final Log log = LogFactory.getLog( LoginServerHandler.class );
  private String lastURI;
  private String keyData;

  // TODO: REFACTOR ALL. ITS ONLY FOR TESTING
  @Override
  public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception
  {
    if( msg instanceof DefaultStsRequest )
    {
      DefaultStsRequest req = (DefaultStsRequest) msg;
      lastURI = req.getUri();
      log.info( "Receive request from client. Method: " + req.getMethod() + "; URI: " + req.getUri() );
    }
    else if( msg instanceof LastHttpContent )
    {
      LastHttpContent content = (LastHttpContent) msg;
      ByteBuf buffer = content.content();

      StringBuilder builder = new StringBuilder();

      while( buffer.isReadable() )
      {
        builder.append( (char) buffer.readByte() );
      }
      log.debug( builder.toString() );

      switch( lastURI )
      {
        case "/Sts/Connect":
          ctx.write( new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK ) );
          break;
        case "/Sts/Ping":
          ctx.write( new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK ) );
          break;
        case "/Auth/LoginStart":

          XStream stream = new XStream();
          stream.processAnnotations( ReplyKeyData.class );

          ReplyKeyData replyKeyData = new ReplyKeyData();
          replyKeyData.setKeyData( "CAAAAECCFuuwk9gFgAAAAPPz8eAzBs/V/75tRz0caaVJQxHWuC7qfyWvHA+nZMQP1MyHNE1UpLfpf6vUJl3dGfGsethsrufh/3xQ/gDi0ISMOG4sPF49k1tIg5hR9RrqTHdyLYWAb5OZWarjZcrmAPP6JGMBqRS4HQvVwJaJpiSrF/SJN7bX+IchUgIYN5Bg" );

          String s = stream.toXML( replyKeyData ).replaceAll( " ", "" );
          byte[] b = s.getBytes();

          DefaultFullStsResponse resp = new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK, Unpooled.wrappedBuffer( b ) );
          resp.headers().set( StsHeaders.Names.CONTENT_LENGTH, b.length );
          resp.headers().set( "s", "7R" );

          ctx.write( resp );

          // TODO:
          break;
        case "/Auth/KeyData":
          // TODO:
          break;
        default:
          log.warn( "No handler available for request " + lastURI );
          ctx.close();
          break;
      }
    }
  }

  @Override
  public void channelReadComplete( ChannelHandlerContext ctx ) throws Exception
  {
    ctx.flush();
  }

  @Override
  public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception
  {
    log.error( "LoginServerHandler: Exception!!!", cause );
    ctx.close();
  }
}
