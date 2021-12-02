package optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author keboom
 * @date 2021/11/29
 */
@AllArgsConstructor
@Getter
public enum Sex {

  man,
  woman;

  public static Sex of(String sex) {
    for (Sex value : Sex.values()) {
      if (value.name().equals(sex)) {
        return value;
      }
    }
    return null;
  }

}
