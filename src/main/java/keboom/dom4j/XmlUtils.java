package keboom.dom4j;

import com.google.common.collect.Sets;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Text;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class XmlUtils {

  /**
   * 可公用 向xml的Document对象的根节点下追加子节点
   *
   * @param doc xml Document对象
   * @param pojo 实体Class类
   */
  private static <T> void addElementForRootNode(Document doc, T object, Class<?> pojo)
      throws IllegalAccessException {

    //获取实体字段数组
    Field[] declaredFields = pojo.getDeclaredFields();
    //获取根节点
    Element ele = doc.getRootElement();
    //建立子节点
    //遍历实体字段属性
    for (Field declaredField : declaredFields) {
      String name = declaredField.getName();
      XmlName annotation = declaredField.getAnnotation(XmlName.class);
      if (annotation != null && StringUtils.isNotEmpty(annotation.value())) {
        name = annotation.value();
      }
      Element pojoNode = ele.addElement(name);
      //开启暴力使用
      declaredField.setAccessible(true);
      //给xml节点属性赋值实体节点属性
      Object data = declaredField.get(object);
      if (Objects.isNull(data)) {
        continue;
      }
      pojoNode.setText(data.toString());
    }
  }


  /**
   * 可公用 Object 解析为xml 的Document对象 通过数据集合与实体字段数组双重遍历,填充xml的节点与节点属性 节点名称为实体类名,节点属性名称为实体字段名称
   *
   * 注意:该方法暂时只支持实体字段为基本类型或自动拆装箱的包装类
   *
   * @param object 数据
   * @param pojo 实体Class
   * @param rootNode 根节点名称
   * @param notNeedSuperDeclaration true：会去除{@code <?xml version="1.0" encoding="UTF-8"?> }的信息
   * @return xml的字符串的值。如果解析失败则返回空
   */
  @Nullable
  public static <T> String fromBeanToXml(
      T object, Class<?> pojo, String rootNode,
      boolean notNeedSuperDeclaration
  ) {
    //创建文档
    Document doc = DocumentHelper.createDocument();
    //创建root节点
    doc.addElement(rootNode);
    //追加数据
    try {
      addElementForRootNode(doc, object, pojo);
    } catch (IllegalAccessException e) {
//      log.error("write to xml has error : {},{},{}", object, pojo, rootNode);
      e.printStackTrace();
      return null;
    }
    OutputFormat format = new OutputFormat();
    format.setSuppressDeclaration(notNeedSuperDeclaration);
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


  /**
   * 可公用 xml解析为对象
   *
   * @param rootEltStr xml字符串的值
   * @param pojo 实体Class
   */
  public static <T> T fromXmlToBean(String rootEltStr, Class<T> pojo) {
    try {
      SAXReader saxReader = new SAXReader();
      Document document = saxReader.read(
          new ByteArrayInputStream(rootEltStr.getBytes(StandardCharsets.UTF_8)));
      Element rootElt = document.getRootElement();
      // 首先得到pojo所定义的字段
      Field[] fields = pojo.getDeclaredFields();
      // 根据传入的Class动态生成pojo对象
      T obj = pojo.newInstance();
      for (Field field : fields) {
        // 设置字段可访问（必须，否则报错）
        field.setAccessible(true);
        // 得到字段的属性名
        String name = field.getName();
        XmlName annotation = field.getAnnotation(XmlName.class);
        if (annotation != null && StringUtils.isNotEmpty(annotation.value())) {
          name = annotation.value();
        }
        // 这一段的作用是如果字段在Element中不存在会抛出异常，如果出异常，则跳过。
        Element element = rootElt.element(name);
        if (element == null || StringUtils.isEmpty(element.getText())) {
          continue;
        }
        String text = element.getText();
        // 根据字段的类型将值转化为相应的类型，并设置到生成的对象中。
        if (field.getType() == Long.class) {
          field.set(obj, Long.parseLong(text));
        } else if (field.getType() == String.class) {
          field.set(obj, text);
        } else if (field.getType() == Double.class) {
          field.set(obj, Double.parseDouble(text));
        } else if (field.getType() == Integer.class) {
          field.set(obj, Integer.parseInt(text));
        } else if (field.getType() == Byte.class) {
          field.set(obj, Byte.parseByte(text));
        } else if (field.getType() == Boolean.class) {
          field.set(obj, Boolean.parseBoolean(text));
        }
      }
      return obj;
    } catch (Exception e) {
//      log.error("has error : {}", rootEltStr);
//      log.error("解析xml失败", e);
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将 map 转换为 xml
   * <p>
   * 例如，存在一个map x，内容为key:value形式，分别有 key1:value1;key2:value2。根节点为  xml，则生成的xml文本为
   *
   * {@code
   * <xml>
   * <key1>value1</key1>
   * <key2>value2</key2>
   * </xml>
   * }
   * </p>
   *
   * @param params map
   * @param xml 转换的xml的根节点
   * @return xml 文本信息
   */
  public static String fromMapToXml(Map<String, Object> params, String xml) {
    Document doc = DocumentHelper.createDocument();
    Element root = doc.addElement(xml);

    for (Entry<String, Object> entry : params.entrySet()) {
      Element element = root.addElement(entry.getKey());


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
      element.setText(entry.getValue().toString());
    }

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

  /**
   * 为根节点添加entry节点
   *
   * @param entry map中的一个entry
   * @param o entry的value对象
   */
  private static void addElementForEntryNode(Element entry, Object o)
      throws IllegalAccessException {
    Field[] declaredFields = o.getClass().getDeclaredFields();
    for (Field declaredField : declaredFields) {
      String name = declaredField.getName();
      XmlName annotation = declaredField.getAnnotation(XmlName.class);
      if (annotation != null && StringUtils.isNotEmpty(annotation.value())) {
        name = annotation.value();
      }
      Element pojoNode = entry.addElement(name);
      declaredField.setAccessible(true);
      Object data = declaredField.get(o);
      if (data == null) {
        pojoNode.setText("");
        continue;
      }
      pojoNode.setText(data.toString());
    }
  }

  /**
   * 将一个xml文件转换为一个map
   *
   * <p>如果xml文本为空，或者结果为空，则返回空map</p>
   *
   * <p>如果存在 xml 为
   * {@code
   * <xml>
   * <key1>value1</key1>
   * <key2>value2</key2>
   * </xml>
   * }
   * 解析后的map key:value形式为：key1:value1;key3:value2
   * </p>
   *
   * @param xml xml文本信息
   * @return map信息
   */
  public static Map<String, Object> fromXmlToMap(String xml) {
    if (StringUtils.isEmpty(xml)) {
      return null;
    }
    try {
      Document document = DocumentHelper.parseText(xml);
      Element root = document.getRootElement();
      List children = root.elements();
      HashMap<String, Object> map = new HashMap<>();
      if (children != null && children.size() > 0) {
        for (int i = 0; i < children.size(); i++) {
          Element child = (Element) children.get(i);
          map.put(child.getName(), child.getTextTrim());
        }
      }
      return map;
    } catch (DocumentException e) {
//      log.error("xml to map failed!!", e);
      e.printStackTrace();
    }
    return null;
  }

  /**
   * bean 转 map
   * @param bean
   * @return
   */
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

  public static <T> T fromMapToBean(Map<String, Object> map, Class<T> bean) {
    try {
      Object instance = bean.newInstance();
      BeanUtils.populate(instance, map);
      return bean.cast(instance);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
//      log.error("map to bean failed! ",e);
    }
    return null;
  }

}
