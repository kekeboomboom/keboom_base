package keboom.datatime;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * java 8 time date  demo
 */
public class Demo1 {

  public static void main(String[] args) {
    LocalDate today = LocalDate.now();

    LocalDate date = LocalDate.of(2020, 11, 2);

//    today.getYear();
//    today.getDayOfMonth();
//    today.getMonthValue();

    Month month = today.getMonth();
    month.getValue();

    int dayOfWeek = today.getDayOfWeek().getValue();

    int dayOfYear = today.getDayOfYear();



    LocalDateTime parse = LocalDateTime.parse("2020-10-10 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

//    LocalDate.parse("2020-20-20");

  }

}
