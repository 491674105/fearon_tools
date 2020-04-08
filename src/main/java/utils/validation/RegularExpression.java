package utils.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @description: 常见正则表达式封装
 * @author: Fearon
 * @create: 2020/4/3 9:31
 **/
public class RegularExpression {
    /**
     * 验证是否为正常手机号
     */
    public static boolean isPhoneNumber(String source) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(147)|(15[^4])|(166)|(17[0-8])|(18[0-9])|(199))\\d{8}$";
        Pattern pattern = Pattern.compile(regExp);
        return pattern.matcher(source).matches();
    }

    /**
     * 校验是否是常规的邮箱格式 email地址，格式：fearon@gmail.com，fearon@xxx.com.cn，xxx代表邮件服务商
     *
     * @param source
     * @return
     */
    public static boolean isNormalMailBox(String source) {
        String regex = "([a-z0-9A-Z]+[-|_|.]?)+[a-z0-9A-Z]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        return matcher.matches();
    }

    /**
     * 校验字符串是否符合数值（实数）要求，匹配所有实数
     * @param source
     * @return
     */
    public static boolean isNumber(String source){
        String regEx = "([\\+]|[-])?[0-9]+(\\.[0-9]+)?";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(source);

        return matcher.matches();
    }

    /**
     * 校验字符串是否为标准日期或日期时间格式（yyyy-MM-dd HH:mm:ss.SSS）
     * @param source
     * @return
     */
    public static boolean isDateTime(String source){
        String regEx = "[0-9]{4}-[0-9]{2}-[0-9]{2}( [0-9]{2}:[0-9]{2}(:[0-9]{2}(.[0-9]+)?)?)?";
        Pattern pattern = Pattern.compile(regEx);

        return pattern.matcher(source).matches();
    }

    /**
     * 校验字符串是否为标准时间格式（HH:mm:ss.SSS）
     * @param source
     * @return
     */
    public static boolean isTime(String source){
        String regEx = "[0-9]{2}:[0-9]{2}(:[0-9]{2}(.[0-9]+)?)?";
        Pattern pattern = Pattern.compile(regEx);

        return pattern.matcher(source).matches();
    }

    public static void main(String[] args) {
        String aa = "-999.01";
        System.out.println(isNumber(aa));

        String bb = "2020-01-01 01:00:00";
        System.out.println(isDateTime(bb));
    }
}
