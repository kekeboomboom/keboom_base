package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import keboom.reflect.Son;
import org.junit.Test;

/**
 * https://www.cnblogs.com/chanshuyi/p/head_first_of_reflection.html
 * @author keboom
 * @date 2021/12/2
 */
public class ReflectTest {

  @Test
  public void getClazz() throws ClassNotFoundException {
    Class clazz1 = Class.forName("keboom.reflect.Son");

    Class<Son> clazz2 = Son.class;

    Son son = new Son(1, "ke");
    Class<? extends Son> clazz3 = son.getClass();

    System.out.println();
  }

  @Test
  public void getField() {
    Class<Son> clazz2 = Son.class;

    Field[] declaredFields = clazz2.getDeclaredFields();

    Field[] field = clazz2.getFields();

    System.out.println();
  }

  @Test
  public void getConstruct() {
    Class<Son> clazz2 = Son.class;

    Constructor<?>[] constructors = clazz2.getConstructors();

    Constructor<?>[] declaredConstructors = clazz2.getDeclaredConstructors();

    System.out.println();
  }

  @Test
  public void getMethods() {
    Class<Son> clazz2 = Son.class;

    Method[] methods = clazz2.getMethods();

    Method[] declaredMethods = clazz2.getDeclaredMethods();

    System.out.println();
  }

  /**
   * 创建 son，赋值，调用方法等
   */
  @Test
  public void demo1()
      throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
    Class clazz1 = Class.forName("keboom.reflect.Son");

    Constructor[] declaredConstructors = clazz1.getDeclaredConstructors();
    Constructor privateConstructor = declaredConstructors[0];
    privateConstructor.setAccessible(true);
    Object instance = privateConstructor.newInstance();

    Field sonAge = clazz1.getDeclaredField("sonAge");
    sonAge.setAccessible(true);
    sonAge.set(instance,15);

    Field sonName = clazz1.getField("sonName");
    sonName.set(instance,"son");

    Method method1Son = clazz1.getDeclaredMethod("method1Son");
    method1Son.setAccessible(true);
    method1Son.invoke(instance);

    System.out.println();
  }

}
