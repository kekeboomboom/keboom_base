package callback.async;

import callback.Callback;
import lombok.SneakyThrows;

/**
 *
 * @author keboom
 * @date 2021/12/22
 */
public class B {

  @SneakyThrows
  public void doSomeThing(Callback callback) {
    System.out.println("B do thing");
    Thread.sleep(3000);
    System.out.println("B finished");
    System.out.println("B callback A");
    callback.onCompleted();
  }

}
