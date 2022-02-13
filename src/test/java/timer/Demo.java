package timer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;
import org.junit.Test;

/**
 *
 * @author keboom 2022/1/3
 */
public class Demo {

  @Test
  public void testTimer() throws InterruptedException, IOException {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        System.out.println("timer task !");
      }
    },1000L,1000L);
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        System.out.println("task 2");
      }
    },2000L,1000L);

    System.out.println(System.in.read());

  }

}
