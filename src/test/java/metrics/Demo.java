package metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 *
 * @author keboom
 * @date 2021/12/23
 */
public class Demo {

  private static Queue<String> queue = new LinkedList<>();

  @SneakyThrows
  @Test
  public void test1() {
    MetricRegistry registry = new MetricRegistry();
    ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(registry).build();
    consoleReporter.start(2, TimeUnit.SECONDS);

    registry.register(MetricRegistry.name(Demo.class, "keboom", "queue"),
                      (Gauge<Integer>) () -> queue.size()
    );
    while (true) {
      queue.add("hello");
      Thread.sleep(500);
    }
  }

}
