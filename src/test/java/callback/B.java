package callback;

/**
 *
 * @author keboom
 * @date 2021/12/22
 */
public class B {

  public void execute(Callback callback) {
    System.out.println("开始执行");
    System.out.println("doing");
    System.out.println("finished");
    callback.onCompleted();
  }

}
