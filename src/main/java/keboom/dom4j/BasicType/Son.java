package keboom.dom4j.BasicType;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 注意要有无参构造函数，否则无法 newinstance
 */
public class Son {

  private String name;

  private Integer age;

  private Boolean sex;

  private GrandSon grandSon;

  private List<Integer> grandSonAge;

}
