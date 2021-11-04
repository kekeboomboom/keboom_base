package keboom.dom4j.BasicType;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Son {

  private String name;

  private Integer age;

  private Boolean sex;

  private GrandSon grandSon;

  private List<Integer> grandSonAge;

}
