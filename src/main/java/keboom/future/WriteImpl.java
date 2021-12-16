package keboom.future;

import java.util.function.Function;
import java.util.function.Supplier;
import keboom.future.impl.HamerImpl;

public class WriteImpl {

   public void fetch(){
      Blog blog = doAction(() -> new Blog("ke", 18));
      System.out.println(blog.toString());
//      Blog blog = new Blog("ke", 18);
//      String name = doFunction(b -> b.getName(), blog);
//      System.out.println(name);
   }

   private <T> T doAction(Supplier<T> supplier){
      return supplier.get();
   }

   private <T, R> R doFunction(Function<T, R> function, T param){
      return function.apply(param);
   }

   public static void main(String[] args) {
      WriteImpl write = new WriteImpl();
      write.fetch();

      Hamer hamer = new HamerImpl();
      hamer.hit(3);
   }

}
