package keboom.guava;

import com.google.common.base.Joiner;
import java.util.HashSet;

public class JoinerDemo {

  public static void main(String[] args) {
    HashSet<Long> set = new HashSet<>();
    set.add(1L);
    set.add(2L);
    set.add(3L);
    set.add(4L);


    System.out.println(Joiner.on(",").join(set));
  }

}
