package openbns.loginserver.net.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 27.01.14
 * Time: 22:03
 */
public abstract class AbstractRequestPacket
{
  protected ByteBuf buf;
  protected Channel channel;

  public abstract void read();

  public abstract void execute();

  public ByteBuf getBuf()
  {
    return buf;
  }

  public void setBuf( ByteBuf buf )
  {
    this.buf = buf;
  }

  public Channel getChannel()
  {
    return channel;
  }

  public void setChannel( Channel channel )
  {
    this.channel = channel;
  }
}
