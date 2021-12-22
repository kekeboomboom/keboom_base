package callback.async;

import callback.Callback;

/**
 *
 * @author keboom
 * @date 2021/12/22
 */
public class A implements Callback {

  public static void execute() {
    B ob = new B();
    A a = new A();
    System.out.println("A : execute");
    System.out.println("异步调用 B -------------------------");
    new Thread(() -> ob.doSomeThing(a)).start();
    System.out.println("A execute finish");
  }

  @Override
  public void onCompleted() {
    System.out.println("A : completed");
  }
}
