package keboom.future;

import java.util.function.Function;

public class FunctionField<T, R> {

  private Function<T,R> func1 = arg -> {
    System.out.println(arg);
    return (R) "hello";
  };

  public static void main(String[] args) {
    FunctionField<String, String> functionField = new FunctionField<>();
    String resutl = functionField.func1.apply("word");
    System.out.println(resutl);

  }

}
