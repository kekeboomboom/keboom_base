package LocalThread;

import org.junit.Test;


public class Test1 {

  @Test
  public void test1() {
    ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();
    threadLocal.set(Boolean.TRUE);
    Boolean aBoolean = threadLocal.get();
    threadLocal.remove();
    Boolean aBoolean1 = threadLocal.get();
    System.out.println(aBoolean1);
  }

  @Test
  public void test2() {
    try {
      System.out.println("hello");
      return;
    } finally {
      System.out.println("hi");
    }
  }

  @Test
  public void test3() {
    int a = 10_00;
    System.out.println(a == 1000);
  }


}
