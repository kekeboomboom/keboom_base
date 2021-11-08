package keboom.dom4j.BasicType;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;

@Data
public class Father {

  private String name;

  private Integer money;

  private Double height;

  private Boolean sex;

  private Byte age;

  private Long id;

  private List<String> sonsName;

  // 暂时规定 map 中 key 和 value 都是 string 吧
  private Map<String, String> sonsFav;

  private Son son;

}
