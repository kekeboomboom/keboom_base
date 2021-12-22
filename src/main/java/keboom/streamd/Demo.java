package keboom.streamd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Demo {

  private IDemo test;

  public static void main(String[] args) {
//    ArrayList<String> list = new ArrayList<>();
//    list.add("ads");
//    list.add("bdc");
//    list.add("ahv");
//    list.add("tsd");
//    list.add("jsd");
//    List<String> strings = list.stream().filter(s -> s.startsWith("a")).map(s -> s.toUpperCase())
//        .collect(Collectors.toList());
//    System.out.println(strings);

    ArrayList<Person> personArrayList = new ArrayList<>();

    personArrayList.add(new Person("ke",22));
    personArrayList.add(new Person("we",23));
    personArrayList.add(new Person("hy",25));
    personArrayList.add(new Person("oe",27));

//    personArrayList.stream().map();

//    List<Integer> e = personArrayList.stream().filter(person -> person.getName().endsWith("e"))
//        .map(person -> ).collect(Collectors.toList());
//    System.out.println(e);


//    Map<String, Person> collect = personArrayList.stream()
//        .collect(Collectors.toMap(Person::getName, Function.identity()));
  }

}
