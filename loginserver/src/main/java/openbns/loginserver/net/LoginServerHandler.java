package openbns.loginserver.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import openbns.commons.net.RC4;
import openbns.commons.net.codec.sts.*;
import openbns.commons.xml.StsXStream;
import openbns.loginserver.crypt.KeyManager;
import openbns.loginserver.net.client.RequestKeyData;
import openbns.loginserver.net.server.ReplyKeyData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Arrays;

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
//      ByteBuf buffer = content.content();

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
          StsXStream str = new StsXStream();
          str.processAnnotations( RequestKeyData.class );

          ByteBuf buffer = content.content();
          StringBuilder builder = new StringBuilder();
          while( buffer.isReadable() )
            builder.append( (char) buffer.readByte() );

          RequestKeyData rkd = (RequestKeyData) str.fromXML( builder.toString() );

          String key = rkd.getKeyData();

          log.debug( key );

          BASE64Decoder decoder = new BASE64Decoder();

          DataInputStream dis = new DataInputStream( new ByteArrayInputStream( decoder.decodeBuffer( key ) ) );
          // RC4 key
          int size = Integer.reverseBytes( dis.readInt() );
          byte[] bytes = new byte[ size ];
          dis.read( bytes );
          inCrypt = new RC4( bytes );

          // Password hash
          size = Integer.reverseBytes( dis.readInt() );
          bytes = new byte[ size ];
          dis.read( bytes );

          log.debug( "Password hash: " + Arrays.toString( bytes ) );
          log.debug( "Password hash: " + new String( bytes  ));

          byte[] client = Arrays.copyOf( bytes, size );
          byte[] server = Arrays.copyOf( bytes, size );

          inCrypt.rc4( client, 0, bytes.length );
          outCrypt.rc4( server, 0, bytes.length );

          log.debug( "AFTER RC4 CL: " + Arrays.toString( client ) );
          log.debug( "AFTER RC4 SR: " + Arrays.toString( server ) );
          log.debug( "SHA HASH=" + Arrays.toString( KeyManager.getInstance().getPasswordHash( "d93799929d" ) ) );

          DefaultFullStsResponse deny = new DefaultFullStsResponse( StsVersion.STS_1_0, StsResponseStatus.NOT_ONLINE );
          ctx.write( deny );

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
//    log.error( "LoginServerHandler: Exception!!!", cause );
    ctx.close();
  }

  @Override
  public void channelRegistered( ChannelHandlerContext ctx ) throws Exception
  {
    log.debug( "Accepted new channel" );
    super.channelRegistered( ctx );
  }
}
