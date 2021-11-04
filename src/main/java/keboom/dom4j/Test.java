package keboom.dom4j;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import keboom.dom4j.BasicType.Father;
import keboom.dom4j.BasicType.GrandSon;
import keboom.dom4j.BasicType.Son;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Test {

  public static void main(String[] args) throws DocumentException {
    Father father = new Father();
//    father.setAge((byte) 25);
    father.setHeight(12.5);
    father.setId(555L);
    father.setMoney(55050);
    father.setName("hehe");
    father.setSex(true);
    father.setSonsName(Lists.newArrayList("keek", "goujie", "chenzelei"));
    father.setSonsAge(Sets.newHashSet(1, 2, 3));
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("ke1", "va1");
    hashMap.put("ke2", "va2");
    father.setSonsFav(hashMap);
    father.setSon(new Son("jack", 18, true, new GrandSon("jack son", 5), Lists.newArrayList(1, 2, 3, 4)));

    Map<String, Object> map = XmlUtils.fromBeanToMap(father);
    String xml = XmlUtils.fromMapToXml(map, "xml");

    String xml2 = Test.fromMapToXml(map, "xml");
    System.out.println(xml);

//    Map<String, Object> map1 = Test.fromXmlToMap(xml2);
//    System.out.println(map1);
//
//    String xml = "<xml>\n"
//        + "  <money>55050</money>\n"
//        + "  <sex>true</sex>\n"
//        + "  <name>keke</name>\n"
//        + "  <id>555</id>\n"
//        + "  <age>25</age>\n"
//        + "  <height>12.5</height>\n"
//        + "</xml>";
//
//    Document document = DocumentHelper.parseText(xml);
//    Element root = document.getRootElement();
//    List<Element> elements = root.elements();
//    for (Element element : elements) {
//      System.out.println(element.getName()+"_____"+element.getTextTrim());
//    }
//
//    Map<String, Object> map1 = XmlUtils.fromXmlToMap(xml);
//    Father father1 = XmlUtils.fromMapToBean(map, Father.class);
//    System.out.println(father1);

//    Father father2 = XmlUtils.fromXmlToBean(xml, Father.class);
//
//    String xml1 = XmlUtils.fromBeanToXml(father, Father.class, "xml", true);
//    System.out.println(xml1);

  }

  private static void addElementToRoot(Map<String, Object> params, Element root) {
    for (Entry<String, Object> entry : params.entrySet()) {

      Object entryValue = entry.getValue();
      if (Objects.isNull(entryValue)) {
        continue;
      }

      if (entryValue instanceof Long || entryValue instanceof Integer || entryValue instanceof String
          || entryValue instanceof Double || entryValue instanceof Byte || entryValue instanceof Boolean
          || entryValue instanceof Short || entryValue instanceof Float || entryValue instanceof Class) {
        Element element = root.addElement(entry.getKey());
        element.setText(entry.getValue().toString());
      } else if (entryValue instanceof Collection) {
        // 假设list中都是基本类型
        Element element = null;
        if (entryValue instanceof List) {
          element = root.addElement("list");
        } else if (entryValue instanceof Set) {
          element = root.addElement("set");
        }
        for (Object o : ((Collection<?>) entryValue)) {
          Element listEle = element.addElement(entry.getKey());
          listEle.setText(o.toString());
        }
      } else if (entryValue instanceof Map) {
        Element element = root.addElement("map");
        for (Entry<?, ?> mapEntry : ((Map<?, ?>) entryValue).entrySet()) {
          Element mapEle = element.addElement((String) mapEntry.getKey());
          mapEle.setText((String) mapEntry.getValue());
        }
      } else { // 如果是普通对象,
        Element element = root.addElement(entry.getKey());
        addElementToRoot(fromBeanToMap(entryValue), element);
      }
    }

  }


  /*
  map list set 中都是基本类型 对象中对象也可以有集合， 就是说暂时先集合里面的泛型都是基本类型，其他暂时一切正常
   */
  public static String fromMapToXml(Map<String, Object> params, String xml) {
    Document doc = DocumentHelper.createDocument();
    Element root = doc.addElement(xml);

    addElementToRoot(params, root);

    // 可能为list，map，set，基本类型，还有一般的bean
    // 如果是一般的bean, 暂时为if true
//      if (true) {
//        element.addElement("key").addText(entry.getKey());
//        Element value = element.addElement("value");
//        // 假如这个对象只有基本类型
//        try {
//          addElementForEntryNode(value, entry.getValue());
//        } catch (IllegalAccessException e) {
//          log.error("map to xml has error : {},{},{}", element, entry.getValue(), root);
//          return null;
//        }
//      }

    // 格式化先
    OutputFormat format = new OutputFormat();
    format.setSuppressDeclaration(true);
    format.setNewlines(true);
    format.setIndent(true);

    try {
      StringWriter out = new StringWriter();
      XMLWriter writer = new XMLWriter(out, format);
      writer.write(doc);
      writer.flush();
      return out.toString();
    } catch (IOException e) {
      throw new RuntimeException("IOException while generating textual "
                                     + "representation: " + e.getMessage());
    }
  }

  public static Map<String, Object> fromBeanToMap(Object bean) {
    BeanMap map = new BeanMap(bean);
    HashMap<String, Object> hashMap = new HashMap<>();
    for (Entry<Object, Object> entry : map.entrySet()) {
      if (entry.getKey().toString().equals("class") || map.get(entry.getKey()) == null) {
        continue;
      }
      hashMap.put(entry.getKey().toString(), entry.getValue());
    }
    return hashMap;
  }


  public static Map<String, Object> fromXmlToMap(String xml, Class beanClass) {
    if (StringUtils.isEmpty(xml)) {
      return null;
    }
    /*

    新思路，我从 xml 中拿到 class，然后通过反射遍历每个 field，这样我可以获得每个 field 的name 和 class 类型。然后就可以创建相应的类型了。



     */
    try {
      Document document = DocumentHelper.parseText(xml);
      Element root = document.getRootElement();
      Element aClass = root.element("class");



      List children = root.elements();
    } catch (DocumentException e) {
      e.printStackTrace();
    }

//    try {
//      Document document = DocumentHelper.parseText(xml);
//      Element root = document.getRootElement();
//      List children = root.elements();
//      HashMap<String, Object> map = new HashMap<>();
//      if (children != null && children.size() > 0) {
//        for (int i = 0; i < children.size(); i++) {
//          Element child = (Element) children.get(i);
//          String childName = child.getName();
//          if (StringUtils.equals(childName, "list")) {
//            ArrayList<Object> list = new ArrayList<>();
//            List<Element> elements = child.elements();
//            for (Element element : elements) {
//              String text = element.getText();
//              Long numLong;
//              Double numDouble;
//              if (null != (numLong = Longs.tryParse(text))){ // 如果是整数，这里先不论是 long 还是 int 、byte、short 等 都先搞成 long 类型
//                list.add(numLong);
//              } else if ((numDouble=Doubles.tryParse(text)) != null) { // 如果是小数，不论是 float 还是 double，都先搞成 double
//                list.add(numDouble);
//              } else if (text.equals("true") || text.equals("false")) {  //
//                list.add(Boolean.parseBoolean(text));
//              } else if (true) { // 暂时集合泛型都为基本类型，最后应该只剩 string 类型了，直接 true ，
//                list.add(text);
//              }
//            }
//            map.put(elements.get(0).getName(),list);
//          } else if (StringUtils.equals(childName, "set")) {
//            HashSet<Object> set = new HashSet<>();
//            List<Element> elements = child.elements();
//            for (Element element : elements) {
//              String text = element.getText();
//              Long numLong;
//              Double numDouble;
//              if (null != (numLong = Longs.tryParse(text))){ // 如果是整数，这里先不论是 long 还是 int 、byte、short 等 都先搞成 long 类型
//                set.add(numLong);
//              } else if ((numDouble=Doubles.tryParse(text)) != null) { // 如果是小数，不论是 float 还是 double，都先搞成 double
//                set.add(numDouble);
//              } else if (text.equals("true") || text.equals("false")) {  //
//                set.add(Boolean.parseBoolean(text));
//              } else if (true) { // 暂时集合泛型都为基本类型，最后应该只剩 string 类型了，直接 true ，
//                set.add(text);
//              }
//            }
//            map.put(elements.get(0).getName(),set);
//          } else if (StringUtils.equals(childName, "map")) {
//            // 暂时规定 map 中 key 和 value 都是 string 吧
//            HashMap<String, String> map1 = new HashMap<>();
//            List<Element> elements = child.elements();
//            for (Element element : elements) {
//              map1.put(element.getName(), element.getText());
//            }
//            map.put(elements.get(0).getName(),map1);
//          } else if (child.elements().size() == 0) { // 这样就是基本类型
//            map.put(childName, child.getText());
//          }
//        }
//      }
//      return map;
//    } catch (DocumentException e) {
////      log.error("xml to map failed!!", e);
//      e.printStackTrace();
//    }
//    return null;
    return null;
  }

//  private

}
