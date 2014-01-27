package openbns.loginserver.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import openbns.commons.net.RC4;
import openbns.commons.net.codec.sts.DefaultStsRequest;
import openbns.commons.net.codec.sts.LastStsContent;
import openbns.loginserver.crypt.KeyManager;
import openbns.loginserver.net.client.AbstractRequestHandler;
import openbns.loginserver.net.client.impl.RequestConnect;
import openbns.loginserver.net.client.impl.RequestKeyData;
import openbns.loginserver.net.client.impl.RequestLoginStart;
import openbns.loginserver.net.client.impl.RequestPing;
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

  private RC4 inCrypt;
  private RC4 outCrypt = new RC4( KeyManager.ND_SERVER_KEY );

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
      AbstractRequestHandler handler;

      switch( lastURI )
      {
        case "/Sts/Connect":
          handler = new RequestConnect();
          break;
        case "/Sts/Ping":
          handler = new RequestPing();
          break;
        case "/Auth/LoginStart":
          handler = new RequestLoginStart();
          break;
        case "/Auth/KeyData":
          handler = new RequestKeyData();
          break;
        default:
          log.warn( "No handler available for request " + lastURI );
          ctx.close();
          return;
      }

      handler.setBuf( content.content() );
      handler.read();
      handler.execute();
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
  }
}
