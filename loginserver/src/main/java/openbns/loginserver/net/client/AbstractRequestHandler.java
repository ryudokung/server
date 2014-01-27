package openbns.loginserver.net.client;

import com.thoughtworks.xstream.XStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import openbns.loginserver.net.LoginServerHandler;

import java.io.ByteArrayInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 22:03
 */
public abstract class AbstractRequestHandler<T>
{
  private LoginServerHandler connection;
  private ByteBuf buf;

  protected Channel channel;
  protected T t;

  public void read()
  {
    XStream stream = new XStream();
    stream.processAnnotations( t.getClass() );

    t = (T) stream.fromXML( new ByteArrayInputStream( buf.array() ), t.getClass() );
  }

  public abstract void execute();

  public LoginServerHandler getConnection()
  {
    return connection;
  }

  public void setConnection( LoginServerHandler connection )
  {
    this.connection = connection;
  }

  public ByteBuf getBuf()
  {
    return buf;
  }

  public void setBuf( ByteBuf buf )
  {
    this.buf = buf;
  }
}
