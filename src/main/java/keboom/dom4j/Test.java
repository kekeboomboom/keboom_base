package keboom.dom4j;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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
        // ??????list?????????????????????

//        if (entryValue instanceof List) {
//          element = root.addElement("list");
//        } else if (entryValue instanceof Set) {
//          element = root.addElement("set");
//        }
        Element element = root.addElement(entry.getKey());
        for (Object o : ((Collection<?>) entryValue)) {
          Element listEle = element.addElement("element");
          listEle.setText(o.toString());
        }
      } else if (entryValue instanceof Map) {
        Element element = root.addElement(entry.getKey());
        for (Entry<?, ?> mapEntry : ((Map<?, ?>) entryValue).entrySet()) {
          Element mapEle = element.addElement((String) mapEntry.getKey());
          mapEle.setText((String) mapEntry.getValue());
        }
      } else { // ?????????????????????,
        Element element = root.addElement(entry.getKey());
        addElementToRoot(fromBeanToMap(entryValue), element);
      }
    }

  }


  /*
  map list set ????????????????????? ???????????????????????????????????? ????????????????????????????????????????????????????????????????????????????????????
   */
  public static String fromMapToXml(Map<String, Object> params, String xml) {
    Document doc = DocumentHelper.createDocument();
    Element root = doc.addElement(xml);

    addElementToRoot(params, root);

    // ?????????list???map???set?????????????????????????????????bean
    // ??????????????????bean, ?????????if true
//      if (true) {
//        element.addElement("key").addText(entry.getKey());
//        Element value = element.addElement("value");
//        // ????????????????????????????????????
//        try {
//          addElementForEntryNode(value, entry.getValue());
//        } catch (IllegalAccessException e) {
//          log.error("map to xml has error : {},{},{}", element, entry.getValue(), root);
//          return null;
//        }
//      }

    // ????????????
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





  /*
  <xml>
  <sonsFav>
    <ke1>va1</ke1>
    <ke2>va2</ke2>
  </sonsFav>
  <money>55050</money>
  <sonsName>
    <element>keek</element>
    <element>goujie</element>
    <element>chenzelei</element>
  </sonsName>
  <sex>true</sex>
  <name>hehe</name>
  <id>555</id>
  <height>12.5</height>
</xml>
   */
  public static Map<String, Object> fromXmlToMap(String xml, Class beanClass) {
    if (StringUtils.isEmpty(xml)) {
      return null;
    }
    /*

    ?????????????????? xml ????????? class????????????????????????????????? field?????????????????????????????? field ???name ??? class ???????????????????????????????????????????????????



     */
    try {

      Document document = DocumentHelper.parseText(xml);
      Element root = document.getRootElement();

      generateMapByElement(root, beanClass);

      System.out.println("ahah");

//      List children = root.elements();
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
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
//              if (null != (numLong = Longs.tryParse(text))){ // ???????????????????????????????????? long ?????? int ???byte???short ??? ???????????? long ??????
//                list.add(numLong);
//              } else if ((numDouble=Doubles.tryParse(text)) != null) { // ??????????????????????????? float ?????? double??????????????? double
//                list.add(numDouble);
//              } else if (text.equals("true") || text.equals("false")) {  //
//                list.add(Boolean.parseBoolean(text));
//              } else if (true) { // ????????????????????????????????????????????????????????? string ?????????????????? true ???
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
//              if (null != (numLong = Longs.tryParse(text))){ // ???????????????????????????????????? long ?????? int ???byte???short ??? ???????????? long ??????
//                set.add(numLong);
//              } else if ((numDouble=Doubles.tryParse(text)) != null) { // ??????????????????????????? float ?????? double??????????????? double
//                set.add(numDouble);
//              } else if (text.equals("true") || text.equals("false")) {  //
//                set.add(Boolean.parseBoolean(text));
//              } else if (true) { // ????????????????????????????????????????????????????????? string ?????????????????? true ???
//                set.add(text);
//              }
//            }
//            map.put(elements.get(0).getName(),set);
//          } else if (StringUtils.equals(childName, "map")) {
//            // ???????????? map ??? key ??? value ?????? string ???
//            HashMap<String, String> map1 = new HashMap<>();
//            List<Element> elements = child.elements();
//            for (Element element : elements) {
//              map1.put(element.getName(), element.getText());
//            }
//            map.put(elements.get(0).getName(),map1);
//          } else if (child.elements().size() == 0) { // ????????????????????????
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

  private static Map<String, Object> generateMapByElement(Element root, Class beanClass)
      throws InstantiationException, IllegalAccessException {
    HashMap<String, Object> resultMap = new HashMap<>();
    Field[] fields = beanClass.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      String fieldName = field.getName();
      Class<?> fieldType = field.getType();
      Element element = root.element(fieldName);
      if (fieldType.equals(Long.class)) {
        resultMap.put(fieldName, Long.parseLong(element.getText()));
      } else if (fieldType.equals(Integer.class)) {
        resultMap.put(fieldName, Integer.parseInt(element.getText()));
      } else if (fieldType.equals(String.class)) {
        resultMap.put(fieldName, element.getText());
      } else if (fieldType.equals(Double.class)) {
        resultMap.put(fieldName, Double.parseDouble(element.getText()));
      } else if (fieldType.equals(Byte.class)) {
        resultMap.put(fieldName, Byte.parseByte(element.getText()));
      } else if (fieldType.equals(Boolean.class)) {
        resultMap.put(fieldName, Boolean.parseBoolean(element.getText()));
      } else if (fieldType.equals(Short.class)) {
        resultMap.put(fieldName, Short.parseShort(element.getText()));
      } else if (fieldType.equals(Float.class)) {
        resultMap.put(fieldName, Float.parseFloat(element.getText()));
      } else if (fieldType.equals(List.class)) {
        Type genericType = field.getGenericType();
//        if (genericType == null) {
//          continue;
//        }
        if (genericType instanceof ParameterizedType) {
          ParameterizedType pt = (ParameterizedType) genericType;
          // ??????????????????class????????????
          Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];

          List<Element> elementList = element.elements();
          // ???????????? integer ??? string
          if (actualTypeArgument.equals(Integer.class)) {
            ArrayList<Integer> curEleList = new ArrayList<>();
            for (Element ele : elementList) {
              curEleList.add(Integer.parseInt(ele.getText()));
            }
            resultMap.put(fieldName, curEleList);
          } else { // ???????????????????????????????????? integer ?????? string ????????????
            ArrayList<String> curEleList = new ArrayList<>();
            for (Element ele : elementList) {
              curEleList.add(ele.getText());
            }
            resultMap.put(fieldName, curEleList);
          }
        }
      } else if (fieldType.equals(Map.class)) { // set ?????????????????????????????? list ??????  map ?????????????????? string???string ??????
        HashMap<String, String> hashMap = new HashMap<>();
        List<Element> elements = element.elements();
        for (Element ele : elements) {
          hashMap.put(ele.getName(), ele.getText());
        }
        resultMap.put(fieldName, hashMap);
      } else { // ????????????????????????????????? list set map ????????????????????????????????????????????????
        Map<String, Object> objectMap = generateMapByElement(element, fieldType);
        Object o = XmlUtils.fromMapToBean(objectMap, fieldType);
        resultMap.put(fieldName, o);
      }
    }

    return resultMap;
  }

//  private

}
