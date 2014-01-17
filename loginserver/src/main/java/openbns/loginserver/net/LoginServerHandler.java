package openbns.loginserver.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import openbns.commons.net.codec.sts.DefaultStsResponse;
import openbns.commons.net.codec.sts.DefaultStsRequest;
import openbns.commons.net.codec.sts.StsResponseStatus;
import openbns.commons.net.codec.sts.StsVersion;
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

  @Override
  public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception
  {
    if( msg instanceof DefaultStsRequest )
    {
      DefaultStsRequest req = (DefaultStsRequest) msg;


      String method = req.getMethod().name();
      switch( method )
      {
        case "/Sts/Connect":
          System.out.println( "Client request conenct" );
          DefaultStsResponse response = new DefaultStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK);
          break;
      }
//      if( is100ContinueExpected( req ) )
//      {
//        ctx.write( new DefaultFullStsResponse( HTTP_1_1, CONTINUE ) );
//      }
//      boolean keepAlive = isKeepAlive( req );
//      FullHttpResponse response = new DefaultFullStsResponse( HTTP_1_1, OK, Unpooled.wrappedBuffer( "Hello world".getBytes() ) );
//      response.headers().set( CONTENT_TYPE, "text/plain" );
//      response.headers().set( CONTENT_LENGTH, response.content().readableBytes() );
//
//      if( !keepAlive )
//      {
//        ctx.write( response ).addListener( ChannelFutureListener.CLOSE );
//      }
//      else
//      {
//        response.headers().set( CONNECTION, Values.KEEP_ALIVE );
//        ctx.write( response );
//      }
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
    cause.printStackTrace();
    ctx.close();
  }
}
