package callback;

/**
 *
 * @author keboom
 * @date 2021/12/22
 */
public class A implements Callback{

  public void doSomething() {
    B ob = new B();
    System.out.println("A : doSomething");
    System.out.println("go to B");
    ob.execute(this);
  }

  @Override
  public void onCompleted() {
    System.out.println("回调了, 回到了 A");
  }
}
