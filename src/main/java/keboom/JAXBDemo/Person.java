package keboom.JAXBDemo;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
public class Person {

  private String name;

  private Son son;

  private List<Integer> luckNum;

  private Map<String,Son> map;

  @XmlElement(name = "nickname")
  public void setName(String name) {
    this.name = name;
  }

}
