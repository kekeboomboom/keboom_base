package metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.google.common.collect.Sets;
import java.nio.channels.Selector;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 *
 * @author keboom
 * @date 2021/12/23
 */
public class MeterTest {

  private static Random random = new Random();

  @SneakyThrows
  @Test
  public void meterTps() {
    MetricRegistry registry = new MetricRegistry();
    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
    reporter.start(1, TimeUnit.SECONDS);

    Meter meter = registry.meter(MetricRegistry.name(MeterTest.class, "keboom", "meter"));

    while (true) {
      request(meter, random.nextInt(10));
      Thread.sleep(600);
    }
  }

  private void request(Meter meter, int nextInt) {
    for (int i = 0; i < nextInt; i++) {
      System.out.print(i + " ");
      meter.mark();
    }
    System.out.println();
  }


  @SneakyThrows
  @Test
  public void test2() {
    System.out.println(1 | 2);
    Selector selector = Selector.open();
    int select = selector.select();
  }

  public void load() {
//    ServiceLoader.load();
  }
}
