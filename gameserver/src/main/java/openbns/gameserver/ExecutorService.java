package openbns.gameserver;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by: Eugene Chipachenko
 * Date: 01.03.14
 */
public class ExecutorService
{
  private Executor executor = Executors.newFixedThreadPool( 2 );
  private Executor scheduledExecutor = Executors.newScheduledThreadPool( 2 );
}
