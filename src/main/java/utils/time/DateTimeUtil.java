package utils.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 日期时间处理工具
 * @author: Fearon
 * @create: 2019-03-26 13:45
 **/
public class DateTimeUtil {
    // 默认日期时间格式 (年-月-日 时:分:秒)
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // 默认日期格式 (年-月-日)
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // 年月日
    public static final DateTimeFormatter DATE_NO_LINE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    // 中文日期格式
    public static final DateTimeFormatter DATE_FORMATTER_CHINESE = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    // 年-月
    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    // 年-周
    public static final DateTimeFormatter YEAR_WEEK_FORMATTER = DateTimeFormatter.ofPattern("yyyy-w");

    /**
     * 获取系统默认时区所形成的偏移量
     *
     * @return
     */
    public static ZoneOffset getDefaultZoneOffset() {
        return ZoneId.systemDefault().getRules().getOffset(Instant.now());
    }

    /**
     * 获取指定日期到对应月一号之间的时间列表
     *
     * @param target_date 指定日期
     * @param isNeedToday 若指定日期处于现实意义的今天，是否需要包含今天的日期
     * @return HashMap
     */
    public static Map<Integer, String> getDateFormatItemToMap(String target_date, boolean isNeedToday) {
        LocalDate _target_date = LocalDate.parse(target_date, DATE_FORMATTER);
        LocalDate _start_date = _target_date.with(TemporalAdjusters.firstDayOfMonth());

        int index = 0;
        Map<Integer, String> result = new HashMap<>();
        LocalDate _now = LocalDate.now();
        int days_differ = after(_target_date, _now);
        for (; ; ) {
            if (after(_start_date, _target_date) <= 0) {
                if (days_differ == 0 && !isNeedToday) {
                    break;
                }

                if (days_differ == 0 || index > 1) {
                    result.put(++index, _start_date.format(DATE_FORMATTER));
                    break;
                }
            }

            result.put(++index, _start_date.format(DATE_FORMATTER));
            _start_date = _start_date.plusDays(1L);
        }
        return result;
    }

    /**
     * 判断结束时间是否在开始时间之后
     *
     * @param _start_date 开始时间
     * @param _end_date   结束时间
     * @return
     */
    public static int after(LocalDate _start_date, LocalDate _end_date) {
        return Long.compare((_end_date.toEpochDay() - _start_date.toEpochDay()), 0);
    }

    /**
     * 为指定年月补充日期（如果是今天或往后则补足今天的日期，否则补足往月的最后一天）
     *
     * @param start
     * @return
     */
    public static String completeDate(String start) {
        StringBuilder _start = new StringBuilder(start);
        _start.append("-");
        _start.append("01");

        LocalDate _start_date = LocalDate.parse(_start, DATE_FORMATTER);
        LocalDate _end_date = LocalDate.now();
        Period period = Period.between(_start_date, _end_date);
        int period_months = period.getMonths();
        if (period_months > 0) {
            _end_date = _start_date.with(TemporalAdjusters.lastDayOfMonth());
        }

        return _end_date.format(DATE_FORMATTER);
    }

    /**
     * 将数据格式化成对应的日期时间
     *
     * @param source
     * @param formatter
     * @param <T>
     * @return
     */
    public static <T> String formatDateTime(T source, String formatter) {
        String result = null;
        if (source instanceof String) {
            return (String) source;
        }

        DateTimeFormatter _user_formatter = DateTimeFormatter.ofPattern(formatter);
        if (source instanceof Instant) {
            result = LocalDateTime.ofInstant((Instant) source, ZoneId.systemDefault()).format(_user_formatter);
        } else if (source instanceof LocalDate) {
            result = ((LocalDate) source).format(_user_formatter);
        } else if (source instanceof LocalDateTime) {
            result = ((LocalDateTime) source).format(_user_formatter);
        } else if (source instanceof Date) {
            Instant instant = ((Date) source).toInstant();
            result = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(_user_formatter);
        } else if (source instanceof Long) {
            Instant instant = Instant.ofEpochMilli((Long) source);
            result = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(_user_formatter);
        }

        return result;
    }

    /**
     * 获取指定年份指定自然周的对应日期区间
     * 规则：（ISO算法标准）
     * 1、第一个自然周要包含周四
     * 2、首个自然周天数 >= 4
     * 3、包含1月4日
     * 4、首个自然周周一所对应的日期必须在去年12月29日到当年1月4日之间
     *
     * @param year          年份
     * @param _week_of_year 一年中的自然周
     * @return
     */
    public static Map<String, LocalDate> getWeekOfYearToDate(int year, int _week_of_year) {
        if (_week_of_year <= 0)
            throw new IllegalArgumentException("自然周必须大于0");

        Map<String, LocalDate> result = new HashMap<>();
        LocalDate start = null;
        LocalDate first = LocalDate.ofYearDay(year, 1);
        int _day_of_week = first.getDayOfWeek().getValue();
        int _day_of_month = first.getDayOfMonth();
        long step = 7L - (long) _day_of_week;
        if (_day_of_week > 4) {
            first = first.plusDays(step + 1);
            step = 6L;
        }

        int _day_of_month_monday = first.minusDays((long) _day_of_week - 1L).getDayOfMonth();
        if (_day_of_month_monday > 4 && _day_of_month_monday < 29) {
            first = first.plusDays(step + 1);
            step = 6L;
        }

        if (_week_of_year == 1) {
            result.put("start", first);
            result.put("end", first.plusDays(step));
        } else {
            start = first.plusDays(step + ((_day_of_month + (((long) _week_of_year - 1L) - 1L) * 7L)));
            result.put("start", start);
            result.put("end", start.plusDays(6L));
        }
        return result;
    }
}
