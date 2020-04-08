package utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 时间数据校验工具
 * @author: Fearon
 * @create: 2018/7/19 10:01
 **/
public class TimeCheckTools {
    private static final long TIMEOUT = 3;

    /**
     * 判断是否超时
     * @param sourceTime 需要判断的时间点
     * @param timeFormatter 时间格式：一类 ---> yyyy-MM-dd HH:mm:ss  二类 ---> yyyy-MM-dd
     * @param timeOut 时限(单位：小时  可填入null；默认3小时)
     * @return true 确认超时
     */
    public static boolean isTimeOut(Object sourceTime, String timeFormatter, Long timeOut){
        try {
            SimpleDateFormat format = new SimpleDateFormat(timeFormatter);
            Date source;
            if(sourceTime instanceof Date) {
                source = format.parse(format.format(sourceTime));
            }else if(sourceTime instanceof String){
                source = format.parse((String) sourceTime);
            }else{
                return false;
            }

            Date nowTime = format.parse(format.format(new Date()));

            // 是否调用默认时限
            if(null == timeOut){
                timeOut = TIMEOUT;
            }
            timeOut *= 60*60*1000;

            // 判断毫秒差与超时的大小
            if((nowTime.getTime() - source.getTime()) <= timeOut)
                return false;
        } catch (ParseException e) {
            System.out.println("数据解析异常！");
        }

        return true;
    }

    /**
     * 校验目标时间到当前是否已是新的一天（北京时间）
     * @param sourceTime 需要判断的时间点
     * @param timeFormatter 时间格式：一类 ---> yyyy-MM-dd HH:mm:ss  二类 ---> yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static boolean isNewDay(Object sourceTime, String timeFormatter) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(timeFormatter);
            Date source;
            if(sourceTime instanceof Date){
                source = format.parse(format.format(sourceTime));
            }else if(sourceTime instanceof String){
                source = format.parse((String) sourceTime);
            }else{
                return false;
            }

            // 获取当前北京时间
            Date nowTime = format.parse(
                    DateTimeTool.getBeijingTime(
                            format.format(new Date())
                    )
            );

            // 获取当前凌晨时间（以北京时间为准）
            long now = nowTime.getTime();
            long daySecond = 60 * 60 * 24 * 1000;
            long dayTime = now - (now + 8 * 3600) % daySecond;

            // 判断当天凌晨时间与目标时间的大小
            if(dayTime - source.getTime() < 0)
                return false;
        } catch (ParseException e) {
            System.out.println("数据解析异常！");
        }

        return true;
    }
}
