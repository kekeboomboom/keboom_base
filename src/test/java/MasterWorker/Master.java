package MasterWorker;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author keboom 2022/2/14
 */
public class Master<T extends Task, R> {


  // https://weread.qq.com/web/reader/9b93254072456ac19b9a176keda32570310beda80a3df77
  private HashMap<String, Worker<T,R>> workers = new HashMap<>();

  private LinkedBlockingQueue<T> taskQueue = new LinkedBlockingQueue();

  private ConcurrentHashMap<String,R> resultMap = new ConcurrentHashMap<>();

  private Thread thread;

  private AtomicLong result = new AtomicLong(0);

  public Master(int workerCount){
    for (int i = 0; i < workerCount; i++) {
      Worker<T, R> worker = new Worker<>();
      workers.put("worker---" + i, worker);
    }
    thread = new Thread(() -> this.execute());
    thread.start();
  }

  private void execute() {
    while (true) {
      for (Entry<String, Worker<T, R>> entry : workers.entrySet()) {
        try {
          T task = taskQueue.take();
          Worker<T, R> worker = entry.getValue();
          worker.submit(task,this::resultCallBack);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void resultCallBack(Object o) {

  }

  public void submit(T task) {
    taskQueue.add(task);
  }
}
