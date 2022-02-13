package kafka;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author keboom
 * @date 2021/12/14
 */
public class ProducerTest {

  private Producer<String, String> producer;

  @Before
  public void setup() {
    Properties prop = new Properties();
    prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.1.4.31:9092");
    prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    prop.setProperty(ProducerConfig.RETRIES_CONFIG, "10");
    prop.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id");
//    prop.setProperty(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,ProducerInterceptorPrefix.class.getName());

    producer = new KafkaProducer<>(prop);

  }


  /**
   * send方法其实都是异步的, 这里调用 get 方法阻塞等待响应, 相当于是同步了
   */
  @Test
  public void sendSync() throws ExecutionException, InterruptedException {
    RecordMetadata recordMetadata = producer.send(new ProducerRecord<>("keboom", "key", "wdnmd")).get();

/*  如果想获取元信息
    Future<RecordMetadata> future = producer.send(new ProducerRecord<>("keboom", "key", "value"));
    RecordMetadata recordMetadata = future.get();
    System.out.println(recordMetadata.toString());
 */
/*  由于返回的是 future, 所以可以控制超时时间
    producer.send(new ProducerRecord<>("keboom", "key", "value")).get(500L, TimeUnit.MILLISECONDS);
 */
  }


  @Test
  public void sendAsync() {

  }

}
