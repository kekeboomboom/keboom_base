package keboom.future.impl;

import keboom.future.Hamer;

/**
 *
 * @author keboom
 * @date 2021/12/2
 */
public class Hammer2Impl implements Hamer {

  /**
   * 锤子击打的次数
   * @param num
   */
  @Override
  public void hit(Integer num) {
    System.out.println("hammer2 hit ");
  }
}
