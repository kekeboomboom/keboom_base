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
public class GrandSon {

  private String name;

  private Integer age;

}
