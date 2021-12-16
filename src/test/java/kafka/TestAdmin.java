package kafka;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreatePartitionsResult;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdmin {

  private AdminClient adminClient;

  @Before
  public void setup() {
    Properties prop = new Properties();
    prop.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"10.1.4.31:9092");

    adminClient = AdminClient.create(prop);
  }

  @Test
  public void list() throws ExecutionException, InterruptedException {
    ListTopicsResult listTopicsResult = adminClient.listTopics();
    KafkaFuture<Set<String>> names = listTopicsResult.names();
    System.out.println(names.get());
  }

  @Test
  public void createTopic() throws ExecutionException, InterruptedException {
    CreateTopicsResult result = adminClient.createTopics(Collections.singleton(new NewTopic("keboom", 5, (short) 1)));
    KafkaFuture<Void> future = result.all();
    future.get();

  }

  @Test
  public void deleteTopic() throws ExecutionException, InterruptedException {
    DeleteTopicsResult result = adminClient.deleteTopics(Collections.singleton("keboom"));
    KafkaFuture<Void> future = result.all();
    future.get();
  }

  @Test
  public void getDes() throws ExecutionException, InterruptedException {
    DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton("keke_test"));
    KafkaFuture<Map<String, TopicDescription>> future = result.all();
    Map<String, TopicDescription> map = future.get();
    TopicDescription topicDescription = map.get("keke_test");
    List<TopicPartitionInfo> partitions = topicDescription.partitions();
    partitions.forEach(System.out::println);
    System.out.println(map.toString());
  }

  /**
   * kafka 不支持减少分区，只能增加分区
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @Test
  public void alterTopic() throws ExecutionException, InterruptedException {
    CreatePartitionsResult result = adminClient.createPartitions(Collections.singletonMap("keke_test",
                                                                                             NewPartitions.increaseTo(3)
    ));
    KafkaFuture<Void> future = result.all();
    future.get();
  }

  @After
  public void close() {
    adminClient.close();
  }
}
