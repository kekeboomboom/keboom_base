package keboom.dom4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.Format;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ConvertUtils {

  public static String objectToXmlStr(Object o) throws IllegalAccessException {
    if (o == null) {
      return null;
    }
    Class<?> aClass = o.getClass();
    Document document = DocumentHelper.createDocument();
    // 添加根节点
    Element root = document.addElement(aClass.getSimpleName());

    for (Field declaredField : aClass.getDeclaredFields()) {
      declaredField.setAccessible(true);
      // 如果是list类型 ParameterizedType表示为list类型
      if (declaredField.getGenericType() instanceof ParameterizedType) {
        Element element = root.addElement(declaredField.getName());
        List<?> obList = (List<?>) declaredField.get(o);
        if (obList != null) {
          ListObjectToXml(obList, element);
        }
      } else {
        String fieldName = declaredField.getName();
        Element element = root.addElement(fieldName);
        element.addText(declaredField.get(o).toString());
      }
    }

    String s = document.asXML();
    return s;
  }

  private static void ListObjectToXml(List<?> obList, Element element) {
    if (obList != null) {
      for (int i = 0; i < obList.size(); i++) {
        Field[] declaredFields = obList.get(i).getClass().getDeclaredFields();

      }
    }
  }

  public static void main(String[] args) throws IllegalAccessException {
    SimpleClass ke = new SimpleClass(1, "ke");
    String res = ConvertUtils.objectToXmlStr(ke);
    System.out.println(res);

  }
}
