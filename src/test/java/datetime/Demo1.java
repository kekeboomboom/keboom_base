package datetime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class Demo1 {

  @Test
  public void dateTest() {
    LocalDate now = LocalDate.now();
    LocalDate adjust = now.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)); // 下周五
    now.with(TemporalAdjusters.lastDayOfMonth());
    LocalDate lastMon = now.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));// 本月最后一个星期五

    System.out.println(adjust);
  }

  @Test
  public void timeTest() {
    LocalTime setTime = LocalTime.of(15, 1, 1);
    LocalTime now = LocalTime.now();
    now.getMinute();

    now.plusHours(1);

    System.out.println(now);
  }


  @Test
  public void dateTimeTest() {
    LocalDateTime.of(2020,1,12,15,36,44);

    LocalDateTime now = LocalDateTime.now();

    LocalDate localDate = now.toLocalDate();
    LocalTime localTime = now.toLocalTime();

    String format = now.format(DateTimeFormatter.ISO_DATE_TIME);
    Instant.now();
    Instant.from(now);
    System.out.println(format);
  }

  @Test
  public void customTemporalAdjuster() {
    LocalDate date = LocalDate.of(2017, 1, 5);
    date.with(temporal -> {
      // 当前日期
      DayOfWeek dayOfWeek = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

      // 正常情况下，每次增加一天
      int dayToAdd = 1;

      // 如果是星期五，增加三天
      if (dayOfWeek == DayOfWeek.FRIDAY) {
        dayToAdd = 3;
      }

      // 如果是星期六，增加两天
      if (dayOfWeek == DayOfWeek.SATURDAY) {
        dayToAdd = 2;
      }

      return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    });
  }


  @Test
  /**
   * 格式化日期
   */
  public void formatDate() {
    LocalDateTime now = LocalDateTime.now();
    String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println(format);

    LocalDateTime parse = LocalDateTime.parse(format,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }


  @Test
  /**
   * date 与 java8中的日期类转换
   */
  public void dateConvert() {
    Date date = new Date();

    // date -- 时间戳
    Instant instant = date.toInstant();
    // 时间戳 ---  date
    Date.from(instant);



    // date
    ZoneId zoneId = ZoneId.systemDefault();
    LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());


  }


  @Test
  public void duration() {
    Duration between = Duration.between(LocalTime.NOON, LocalTime.now());
    System.out.println(between.get(ChronoUnit.SECONDS));

    Duration duration = Duration.ofMillis(50);
    System.out.println(duration.toMillis());
  }

  @Test
  public void compare() {
    Date now = new Date();
    Date yesterday = DateUtils.addDays(now, -1);
    // i 只有 0 1 -1 这三种
    int i = now.compareTo(yesterday);
    if (i > 0) {
      System.out.println("今天 自然比昨天大呀！");
    } else {
      System.out.println("不对经");
    }
    System.out.println();
  }

}
