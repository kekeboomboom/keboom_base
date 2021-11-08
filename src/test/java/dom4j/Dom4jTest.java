package dom4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import keboom.dom4j.BasicType.Father;
import keboom.dom4j.BasicType.GrandSon;
import keboom.dom4j.ConvertUtils;
import keboom.dom4j.XmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

public class Dom4jTest {

  @Test
  public void xmltoMap() {
    String xml = "\n"
        + "<xml>\n"
        + "  <son>\n"
        + "    <grandSonAge>\n"
        + "      <element>1</element>\n"
        + "      <element>2</element>\n"
        + "      <element>3</element>\n"
        + "      <element>4</element>\n"
        + "    </grandSonAge>\n"
        + "    <grandSon>\n"
        + "      <name>jack son</name>\n"
        + "      <age>5</age>\n"
        + "    </grandSon>\n"
        + "    <sex>true</sex>\n"
        + "    <name>jack</name>\n"
        + "    <age>18</age>\n"
        + "  </son>\n"
        + "  <sonsFav>\n"
        + "    <ke1>va1</ke1>\n"
        + "    <ke2>va2</ke2>\n"
        + "  </sonsFav>\n"
        + "  <money>55050</money>\n"
        + "  <sonsName>\n"
        + "    <element>keek</element>\n"
        + "    <element>goujie</element>\n"
        + "    <element>chenzelei</element>\n"
        + "  </sonsName>\n"
        + "  <sex>true</sex>\n"
        + "  <name>hehe</name>\n"
        + "  <id>555</id>\n"
        + "  <height>12.5</height>\n"
        + "</xml>\n";
    Map<String, Object> map = Dom4jTest.fromXmlToMap(xml, Father.class);
    Father father = XmlUtils.fromMapToBean(map, Father.class);
    System.out.println(father);
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

      return generateMapByElement(root, beanClass);
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
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
      if (element == null) {
        continue;
      }
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
          // 得到泛型里的class类型对象
          Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];

          List<Element> elementList = element.elements();
          // 先只支持 integer 和 string
          if (actualTypeArgument.equals(Integer.class)) {
            ArrayList<Integer> curEleList = new ArrayList<>();
            for (Element ele : elementList) {
              curEleList.add(Integer.parseInt(ele.getText()));
            }
            resultMap.put(fieldName, curEleList);
          } else { // 先只支持那两种，因此不是 integer 就是 string 了哈。。
            ArrayList<String> curEleList = new ArrayList<>();
            for (Element ele : elementList) {
              curEleList.add(ele.getText());
            }
            resultMap.put(fieldName, curEleList);
          }
        }
      } else if (fieldType.equals(Map.class)) { // set 不去支持了，仅仅支持 list 吧。  map 也先仅仅支持 string，string 吧。
        HashMap<String, String> hashMap = new HashMap<>();
        List<Element> elements = element.elements();
        for (Element ele : elements) {
          hashMap.put(ele.getName(), ele.getText());
        }
        resultMap.put(fieldName, hashMap);
      }else { // 那么排除基本类型，还有 list set map ，那么应该就剩我们自定义的对象了
        Map<String, Object> objectMap = generateMapByElement(element, fieldType);
        Object o = XmlUtils.fromMapToBean(objectMap, fieldType);
        resultMap.put(fieldName, o);
      }
    }

    return resultMap;
  }

  @Test
  public void testGson() {
    GrandSon ke = new GrandSon("ke", 2);
    Map<String, Object> stringObjectMap = XmlUtils.fromBeanToMap(ke);
    System.out.println(stringObjectMap);



//    Map<String, Object> objectHashMap = new HashMap<>();
//    objectHashMap.put("name", "ke");
//    objectHashMap.put("age",new Integer(2));
    GrandSon grandSon = XmlUtils.fromMapToBean(stringObjectMap, GrandSon.class);
    System.out.println(grandSon);


  }
}
