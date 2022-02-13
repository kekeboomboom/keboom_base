package metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 *
 * @author keboom
 * @date 2021/12/23
 */
public class HistogramsTest {

  private static Random random = new Random();

  @SneakyThrows
  @Test
  public void test1() {
    MetricRegistry registry = new MetricRegistry();
    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
    reporter.start(1, TimeUnit.SECONDS);

    Histogram histogram = new Histogram(new ExponentiallyDecayingReservoir());
    registry.register(MetricRegistry.name(HistogramsTest.class, "keboom", "histograms"), histogram);

    while (true) {
      long before = System.currentTimeMillis();
      Thread.sleep(random.nextInt(100) + 100L);
      long after = System.currentTimeMillis();
      histogram.update(after - before);
    }
  }

}
