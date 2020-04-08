package utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Date：2017/2/28
 * Time: 15:48
 */
public class DateTimeTool {
    /**
     * createTime：2017/2/28
     * Description:取北京时间
     *
     * @param str：时间格式
     * @return 时间
     */
    public static String getBeijingTime(String str) {
        return getFormatedDateString(8, str);
    }

    /**
     * createTime：2017/2/28
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     *
     * @param timeZoneOffset：时区 str:时间格式
     * @return
     */
    public static String getFormatedDateString(float timeZoneOffset, String str) {
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }

        int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    /**
     * createTime：2017/2/28
     * 获取当前时间的前后几天
     *
     * @param number：几天 str :时间格式
     * @return String
     */
    public static String getDateByNumber(int number, String str) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String date1 = getBeijingTime(str);
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        Date date = sdf.parse(date1);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * createTime: 2017/2/28
     * 获取某个时间和当前时间的
     *
     * @param time String
     * @return long
     */
    public static long getdisTime(String time) throws ParseException {
        long dis = 0;
        String now = getBeijingTime("yyyyMMddHHmm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        long nowtime = sdf.parse(now).getTime();
        long resTime = sdf.parse(time).getTime();
        dis = resTime - nowtime;
        return dis;
    }

    public static long getdisTime1(String time) throws ParseException {
        long dis = 0;
        String now = getBeijingTime("yyyyMMddHHmm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        long nowtime = sdf.parse(now).getTime();
        long resTime = sdf.parse(time).getTime();
        dis = nowtime - resTime;
        return dis;
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
