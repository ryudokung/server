package openbns.loginserver.net.client.impl;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import openbns.commons.net.codec.sts.*;
import openbns.commons.xml.StsXStream;
import openbns.loginserver.dao.AccountDAO;
import openbns.loginserver.model.Account;
import openbns.loginserver.net.Session;
import openbns.loginserver.net.client.AbstractRequestPacket;
import openbns.loginserver.net.client.dto.LoginStartDTO;
import openbns.loginserver.net.server.dto.ReplyKeyData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:11
 */
public class RequestLoginStart extends AbstractRequestPacket
{
  private static final Log log = LogFactory.getLog( RequestLoginStart.class );
  private LoginStartDTO loginStart;

  @Override
  public void read()
  {
    StsXStream stream = new StsXStream();
    stream.processAnnotations( LoginStartDTO.class );

    loginStart = (LoginStartDTO) stream.fromXML( new ByteBufInputStream( buf ) );
    log.debug( "Read from client object: " + loginStart );
  }

  @Override
  public void execute()
  {
    Session session = getHandler().getSession();

    // TODO:
//    session.setAccount(  );

    try
    {
      Account account = AccountDAO.getInstance().getByLogin( loginStart.getLoginName() );
      session.setAccount( account );

      BigInteger key = session.generateServerExchangeKey();
      BigInteger sessionKey = session.getSessionKey();

      byte[] bk = key.toByteArray();
      byte[] sk = sessionKey.toByteArray();

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream( outputStream );
      dos.writeInt( sk.length );
      dos.write( sk );
      dos.writeInt( bk.length );
      dos.write( bk );
      dos.flush();
      dos.close();

      byte[] array = outputStream.toByteArray();

      BASE64Encoder encoder = new BASE64Encoder();
      String kd = encoder.encode( array );

      ReplyKeyData replyKeyData = new ReplyKeyData();
      replyKeyData.setKeyData( kd );

      StsXStream stream = new StsXStream();
      stream.processAnnotations( ReplyKeyData.class );
      byte[] b = stream.toXML( replyKeyData ).getBytes();

      DefaultStsResponse resp = new DefaultStsResponse( StsVersion.STS_1_0, StsResponseStatus.OK );
      resp.headers().add( StsHeaders.Names.CONTENT_LENGTH, b.length );
      resp.headers().add( StsHeaders.Names.SESSION_NUMBER, session.getSessionId() + "R" );

      channel.write( resp );
      channel.write( new DefaultLastStsContent( Unpooled.wrappedBuffer( b ) ) );
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
  }
}
