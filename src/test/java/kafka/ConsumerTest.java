package kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author keboom
 * @date 2021/12/15
 */
public class ConsumerTest {

  private KafkaConsumer<String, String> consumer;

  @Before
  public void setup() {
    Properties prop = new Properties();
    prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.1.4.31:9092");
    prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    prop.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-ke");
    prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-consumer");
    consumer = new KafkaConsumer<>(prop);

  }

  @Test
  public void rev() {
    consumer.subscribe(Collections.singletonList("keboom"));
    while (true) {
      ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(3));
      Iterable<ConsumerRecord<String, String>> iterable = poll.records("keboom");
      iterable.forEach(System.out::println);
      if (poll.isEmpty()) {
        break;
      }
    }
  }

}
