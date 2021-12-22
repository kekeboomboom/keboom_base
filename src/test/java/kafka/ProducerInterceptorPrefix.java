package kafka;

import java.util.Map;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 *
 * @author keboom
 * @date 2021/12/22
 */
public class ProducerInterceptorPrefix implements ProducerInterceptor<String, String> {

  private volatile long sendSuccess = 0;
  private volatile long sendFailure = 0;

  @Override
  public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
    String value = record.value();

    return new ProducerRecord<>(record.topic(),
                                record.partition(),
                                record.timestamp(),
                                record.key(),
                                "prefix - " + value,
                                record.headers()
    );
  }

  @Override
  public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

  }

  /**
   * This is called when interceptor is closed
   */
  @Override
  public void close() {

  }

  /**
   * Configure this class with the given key-value pairs
   * @param configs
   */
  @Override
  public void configure(Map<String, ?> configs) {

  }
}
