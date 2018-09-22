package utils.phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PhonePandTool {
	/**
	 * 验证是否为正常手机号
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	 public static boolean isPhoneNumber(String str) throws PatternSyntaxException {  
	        String regExp = "^((13[0-9])|(147)|(15[^4])|(166)|(17[0-8])|(18[0-9])|(199))\\d{8}$";
	        Pattern p = Pattern.compile(regExp);  
	        Matcher m = p.matcher(str);  
	        return m.matches();  
	 }

	public static void main(String[] args) {
		System.out.println(isPhoneNumber("16668348756"));
	}
}
