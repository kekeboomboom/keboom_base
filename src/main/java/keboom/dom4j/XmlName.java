package keboom.dom4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlName {

  /**
   * xml 序列化和放序列化的名称
   *
   * @return 最终序列化的名称
   */
  String value();

}
