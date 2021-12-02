package FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;

public class LineTest {

  @Test
  public void test1() throws IOException {
    LineIterator lineIterator = FileUtils.lineIterator(new File("/Users/keboom/Work/IdeaProjects/keboom_demo/src/test/java/FileUtils/test.txt"));
    while (lineIterator.hasNext()) {
      String next = lineIterator.next();
      System.out.println(next);
    }
  }

  @Test
  public void stream() {
    ArrayList<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    List<Integer> collect = list.stream().filter(item -> item == 2).collect(Collectors.toList());
    System.out.println(collect);
  }

  @Test
  public void time() {
    System.out.println(new Date(2021,11,16).getTime());
  }

}
