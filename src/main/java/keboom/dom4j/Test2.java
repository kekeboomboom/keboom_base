package keboom.dom4j;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;
import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.type.PrimitiveType;
import keboom.dom4j.BasicType.Father;
import keboom.dom4j.BasicType.GrandSon;
import keboom.dom4j.BasicType.Son;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Test2 {

  public static void main(String[] args)
      throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException {

    //class -> {Class@477} "class keboom.dom4j.BasicType.Father"
//    Class<?> clazz = Class.forName("keboom.dom4j.BasicType.Father");
//    Field[] declaredFields = clazz.getDeclaredFields();
//    HashMap<String, Class<?>> myObjectMap = new HashMap<>();
//    for (Field declaredField : declaredFields) {
//      Class<?> type = declaredField.getType();
//      String name = declaredField.getName();
//      System.out.println(name + "----" + type);
//      if (type == Long.class || type == Integer.class || type == String.class || type == Double.class
//          || type == Byte.class || type == Boolean.class || type == Short.class || type == Float.class
//          || type == List.class || type == Set.class || type == Map.class) {
//        continue;
//      }
//      myObjectMap.put(name, type);
//    }
//    System.out.println(myObjectMap);
/*
    GrandSon grandSon = new GrandSon("ke",1,Lists.newArrayList(1));

    Class<? extends GrandSon> aClass = grandSon.getClass();
    Field[] declaredFields = aClass.getDeclaredFields();
    for (Field field : declaredFields) {
      Type genericType = field.getGenericType();
      if (genericType == null) {
        continue;
      }
      if (genericType instanceof ParameterizedType) {
        ParameterizedType pt = (ParameterizedType) genericType;
        // 得到泛型里的class类型对象
        Class<?> actualTypeArgument = (Class<?>)pt.getActualTypeArguments()[0];

        if (actualTypeArgument.equals(Integer.class)){
          System.out.println("e");
        }

        List<Object> curEleList = new ArrayList<>();
//        Object actualType = actualTypeArgument.newInstance();
        //....actualType字段处理
//        curEleList.add(actualType);

        System.out.println(curEleList);
      }
    }

    System.out.println();

 */
  }


//  public void strToPrimitive(String str, Class primitiveClass) {
//    if (primitiveClass.equals(Long.class)){
//      resultMap.put(primitiveClass,Long.parseLong(str));
//    } else if (primitiveClass.equals(Integer.class)) {
//      resultMap.put(fieldName, Integer.parseInt(element.getText()));
//    }else if (primitiveClass.equals(String.class)) {
//      resultMap.put(fieldName, element.getText());
//    }else if (primitiveClass.equals(Double.class)) {
//      resultMap.put(fieldName, Double.parseDouble(element.getText()));
//    }else if (primitiveClass.equals(Byte.class)) {
//      resultMap.put(fieldName, Byte.parseByte(element.getText()));
//    }else if (primitiveClass.equals(Boolean.class)) {
//      resultMap.put(fieldName, Boolean.parseBoolean(element.getText()));
//    }else if (primitiveClass.equals(Short.class)) {
//      resultMap.put(fieldName, Short.parseShort(element.getText()));
//    }else if (primitiveClass.equals(Float.class)) {
//      resultMap.put(fieldName, Float.parseFloat(element.getText()));
//  }


}


