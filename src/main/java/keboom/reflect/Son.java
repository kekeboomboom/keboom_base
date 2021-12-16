package keboom.reflect;

import lombok.ToString;

/**
 *
 * @author keboom
 * @date 2021/12/2
 */
@ToString
public class Son extends Father{

  private Integer sonAge;

  public String sonName;

  private Son() {

  }

  public Son(Integer sonAge, String sonName) {
    this.sonAge = sonAge;
    this.sonName = sonName;
  }

  private void method1Son() {
    System.out.println(this.toString());
  }

  public void method2Son() {
    System.out.println("son method2");
  }

}
