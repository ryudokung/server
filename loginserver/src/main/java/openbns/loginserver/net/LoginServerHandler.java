package openbns.loginserver.net;

import com.thoughtworks.xstream.XStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import openbns.commons.net.codec.sts.*;
import openbns.commons.xml.StsXStream;
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
  private int session;

  // TODO: REFACTOR ALL. ITS ONLY FOR TESTING
  @Override
  public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception
  {
    if( msg instanceof DefaultStsRequest )
    {
      DefaultStsRequest req = (DefaultStsRequest) msg;
      lastURI = req.getUri();
      log.info( "Receive request from client. Method: " + req.getMethod() + "; URI: " + req.getUri() );

      String s = req.headers().get( "s" );
      if( s != null )
        session = Integer.parseInt( s );
    }
    else if( msg instanceof LastStsContent )
    {
      LastStsContent content = (LastStsContent) msg;
      ByteBuf buffer = content.content();

//      StringBuilder builder = new StringBuilder();
//
//      while( buffer.isReadable() )
//      {
//        builder.append( (char) buffer.readByte() );
//      }
//      log.debug( builder.toString() );

      switch( lastURI )
      {
        case "/Sts/Connect":
          ctx.write( new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK ) );
          break;
        case "/Sts/Ping":
          ctx.write( new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK ) );
          break;
        case "/Auth/LoginStart":
          StsXStream stream = new StsXStream();
          stream.processAnnotations( ReplyKeyData.class );

          ReplyKeyData replyKeyData = new ReplyKeyData();
          replyKeyData.setKeyData( "CAAAAECCFuuwk9gFgAAAAPPz8eAzBs/V/75tRz0caaVJQxHWuC7qfyWvHA+nZMQP1MyHNE1UpLfpf6vUJl3dGfGsethsrufh/3xQ/gDi0ISMOG4sPF49k1tIg5hR9RrqTHdyLYWAb5OZWarjZcrmAPP6JGMBqRS4HQvVwJaJpiSrF/SJN7bX+IchUgIYN5Bg" );

          byte[] b = stream.toXML( replyKeyData ).getBytes();

          DefaultStsResponse resp = new DefaultStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK );
          resp.headers().add( StsHeaders.Names.CONTENT_LENGTH, b.length );
          resp.headers().add( "s", session + "R" );
          ctx.writeAndFlush( resp );
          ctx.writeAndFlush( new DefaultLastStsContent( Unpooled.wrappedBuffer( b ) ) );
          break;
        case "/Auth/KeyData":
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

  @Override
  public void channelRegistered( ChannelHandlerContext ctx ) throws Exception
  {
    log.debug( "Accepted new channel" );
    super.channelRegistered( ctx );
  }
}
