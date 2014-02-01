package openbns.loginserver.net.client.impl;

import io.netty.buffer.ByteBufInputStream;
import openbns.commons.util.CryptUtil;
import openbns.commons.xml.StsXStream;
import openbns.loginserver.net.client.AbstractRequestPacket;
import openbns.loginserver.net.client.dto.KeyDataDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 21:14
 */
public class RequestKeyData extends AbstractRequestPacket
{
  private static final Log log = LogFactory.getLog( RequestKeyData.class );
  private KeyDataDTO keyData;

  @Override
  public void read()
  {
    StsXStream stream = new StsXStream();
    stream.processAnnotations( KeyDataDTO.class );

    keyData = (KeyDataDTO) stream.fromXML( new ByteBufInputStream( buf ) );
    log.debug( "Read from client object: " + keyData );
  }

  @Override
  public void execute()
  {
    byte[] data = CryptUtil.base64( keyData.getKeyData() );
    ByteBuffer bf = ByteBuffer.wrap( data );
    bf.order( ByteOrder.LITTLE_ENDIAN );

    int size1 = bf.getInt();
    byte[] buffer1 = new byte[size1];
    bf.get( buffer1 );

    int size2 = bf.getInt();
    byte[] buffer2 = new byte[size2];
    bf.get( buffer2 );

    System.out.println( new String( buffer1 ) );
    System.out.println( new String( buffer2 ) );
  }
}
