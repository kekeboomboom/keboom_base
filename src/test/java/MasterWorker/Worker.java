package MasterWorker;

import java.util.function.Consumer;

/**
 *
 * @author keboom 2022/2/14
 */
public class Worker<T extends Task, R> {


  public void submit(T task, Consumer<R> action) {

  }
}
