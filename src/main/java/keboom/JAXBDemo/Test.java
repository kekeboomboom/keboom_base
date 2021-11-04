package keboom.JAXBDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Test {

  public static void main(String[] args) {

    HashMap<String, Son> map = new HashMap<>();
    map.put("ke",new Son("keson",15));
    map.put("boom",new Son("bomson",19));
    Person person = new Person("ke", new Son("keson",5), new ArrayList<Integer>(Arrays.asList(7, 77, 777)),map);
    String xmlStr = XmlObjectConverter.objectToXmlStr(person);
    System.out.println(xmlStr);




//    String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
//        + "<person>\n"
//        + "    <luckNum>7</luckNum>\n"
//        + "    <luckNum>77</luckNum>\n"
//        + "    <luckNum>777</luckNum>\n"
//        + "    <name>ke</name>\n"
//        + "    <son>\n"
//        + "        <age>5</age>\n"
//        + "        <name>keson</name>\n"
//        + "    </son>\n"
//        + "</person>";
//    Person o =(Person) converter.xmlStrToObject(xmlStr, Person.class);
//    System.out.println(o.toString());
  }

}
