package openbns;

import openbns.loginserver.crypt.KeyManager;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 20.01.14
 * Time: 6:20
 */
public class Test
{
  public static void main( String[] args ) throws IOException, NoSuchAlgorithmException

  {
//    String s = "CAAAAECCFuuwk9gFgAAAAPPz8eAzBs/V/75tRz0caaVJQxHWuC7qfyWvHA+nZMQP1MyHNE1UpLfpf6vUJl3dGfGsethsrufh/3xQ/gDi0ISMOG4sPF49k1tIg5hR9RrqTHdyLYWAb5OZWarjZcrmAPP6JGMBqRS4HQvVwJaJpiSrF/SJN7bX+IchUgIYN5Bg";
//    String s = "CAAAAECCFuuwk9gFgAAAAPPz8eAzBs/V/75tRz0caaVJQxHWuC7qfyWvHA+nZMQP1MyHNE1UpLfpf6vUJl3dGfGsethsrufh/3xQ/gDi0ISMOG4sPF49k1tIg5hR9RrqTHdyLYWAb5OZWarjZcrmAPP6JGMBqRS4HQvVwJaJpiSrF/SJN7bX+IchUgIYN5Bg";
    String s = "BAAAAAEAAQAAAQAAVTj2i7eswaY0/+38rvw/FSpxeliquhqy5RuqJ1D+TogcKkoZ3sG1qGEypaibl2rI4wFKNJU8UgydhNt6aBDLC+g4wTawk2y7bAz9N2PgU2jnNEP94RHLratcg9YzcRfcVnoPcEa/KkZvHoyWFJ6MS0Pa9DGIC2GHWXT+WFSwjnk/1hbBRlNo71/13Vnv8f9qgxJTZD7KBg3QKxS6fLgkENI0sWDu3zd4yYE5bFfBUwlWcW4HfMnPGYgUgfyTNOLBDOFgwmsEXYYUgwPBJ6fV6wyU1ALdYy/1qMDOWN2D/sE4IOLVzcdjRlmnxmW/GWff+ngpjXvTdr9rywiIHrFFzg==";

    BASE64Decoder decoder = new BASE64Decoder();
    byte[] b = decoder.decodeBuffer( s );

    DataInputStream dis = new DataInputStream( new ByteArrayInputStream( b ) );

    int size_1 = Integer.reverseBytes( dis.readInt() );
    byte[] key_1 = new byte[ size_1 ];
    dis.read( key_1 );

    int size_2 = Integer.reverseBytes( dis.readInt() );
    byte[] key_2 = new byte[ size_2 ];
    dis.read( key_2 );

    System.out.println( Arrays.toString( key_1 ) );
    System.out.println( Arrays.toString( key_2 ) );

    byte[] hash = KeyManager.getInstance().getLoginPasswordHash( "root", "root" );
    System.out.println( Arrays.toString( hash ) );
  }
}
