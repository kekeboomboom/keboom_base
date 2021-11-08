package optional;

import com.google.common.base.Objects;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import keboom.optional.Person;
import org.junit.Test;

public class Demo1 {

  @Test
  public void filter() {
    new ArrayList<Integer>();
    List<Integer> list = new ArrayList<>();
    list.add(255);
    list.add(500);
    list.add(100);

//    Map<Integer, Person> personMap = Optional.ofNullable(list).orElse(Collections.emptyList())
//        .stream()
//        .map(age -> new Person(age, age + ""))
//        .collect(Collectors.toMap(Person::getAge, Function.identity()));

//    System.out.println(personMap);

  }

  @Test
  public void testNull() {
    String s = Instant.now().toString();
    System.out.println(s);
  }

  @Test
  public void testForEach() {
    HashSet<String> set = new HashSet<>();
    set.add("aa");
    set.add("a2");
    set.add("ab");
    set.add("ah");
    set.forEach(System.out::println);
  }

}
