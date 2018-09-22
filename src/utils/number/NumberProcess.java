package utils.number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 数值数据处理工具
 * @author: Fearon
 * @create: 2018/9/8 11:32
 **/
public class NumberProcess {
    /**
     * 校验字符串是否符合数值要求
     * @param source_str
     * @return
     */
    public static boolean isNumber(String source_str){
        String regEx = "[0-9]+(.[0-9]+)";
        Pattern pattern = Pattern.compile(regEx);

        Matcher matcher = pattern.matcher(source_str);

        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isNumber("01230.456"));
    }
}
