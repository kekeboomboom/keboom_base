package keboom.dom4j;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;
import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import keboom.dom4j.BasicType.Father;
import keboom.dom4j.BasicType.GrandSon;
import keboom.dom4j.BasicType.Son;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Test2 {

  public static void main(String[] args) throws DocumentException, ClassNotFoundException {

    //class -> {Class@477} "class keboom.dom4j.BasicType.Father"
    Class<?> clazz = Class.forName("keboom.dom4j.BasicType.Father");
    Field[] declaredFields = clazz.getDeclaredFields();
    HashMap<String, Class<?>> myObjectMap = new HashMap<>();
    for (Field declaredField : declaredFields) {
      Class<?> type = declaredField.getType();
      String name = declaredField.getName();
      System.out.println(name + "----" + type);
      if (type == Long.class || type == Integer.class || type == String.class || type == Double.class
          || type == Byte.class || type == Boolean.class || type == Short.class || type == Float.class
          || type == List.class || type == Set.class || type == Map.class) {
        continue;
      }
      myObjectMap.put(name, type);
    }
    System.out.println(myObjectMap);
  }




}


