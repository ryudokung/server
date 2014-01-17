package openbns.loginserver.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import openbns.commons.net.codec.sts.DefaultStsRequest;
import openbns.commons.net.codec.sts.DefaultStsResponse;
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
          DefaultStsResponse response = new DefaultStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK );
          ctx.write( response );
          break;
        case "/Sts/Ping":
          // TODO:
          break;
        case "/Auth/LoginStart":
          // TODO:
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
    cause.printStackTrace();
    ctx.close();
  }
}
