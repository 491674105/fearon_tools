package utils.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 邮箱信息处理
 * @author: Fearon
 * @create: 2018/9/11 17:42
 **/
public class MailBoxProcess {
    /**
     * 校验是否是常规的邮箱格式 email地址，格式：zhang@gmail.com，zhang@xxx.com.cn，xxx代表邮件服务商
     * @param mailbox
     * @return
     */
    public static boolean isNormalMailBox(String mailbox){
        String regex = "([a-z0-9A-Z]+[-|_|.]?)+[a-z0-9A-Z]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mailbox);

        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isNormalMailBox("123@qq.com"));
    }
}
