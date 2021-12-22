package callback;

import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author keboom
 * @date 2021/12/22
 */
public class Main {

  /**
   * 这样算是同步回调
   */
  @Test
  public void test() {
    A a = new A();
    a.doSomething();
  }

  /**
   * 异步回调
   */
  @Test
  public void test2() throws IOException {
    callback.async.A.execute();
    System.out.println(System.in.read());
  }

}
