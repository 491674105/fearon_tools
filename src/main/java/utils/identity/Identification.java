package utils.identity;

import utils.time.DateTimeUtil;

/**
 * @description: 身份证号校验及解析
 * @author: Fearon
 * @create: 2018/7/31 15:56
 **/
public class Identification {
    // 身份证号加权因子（排除第18的校验位）
    private static final int[] base_number = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 校验码
    private static final char[] check_code = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    /**
     * 身份证校验
     *
     * @param idCard
     * @return
     */
    public static boolean idCardCheck(String idCard) {
        if (idCard == null || idCard.trim().length() == 0)
            return false;

        int length = idCard.trim().length();
        if (length != 18)
            return false;

        char[] idCard_number = idCard.toCharArray();
        int sum = 0;
        int i = 0;
        for (; i < 17; i++) {
            sum += (idCard_number[i] - 48) * base_number[i];
        }
        int remainder = sum % 11;
        char check_coder = check_code[remainder];
        char id_coder = idCard_number[17];

        if (check_coder == 'X') {
            return id_coder == 'X' || id_coder == 'x';
        } else {
            return check_coder == id_coder;
        }
    }

    /**
     * 解析身份证中的年龄
     *
     * @param idCard
     * @return
     */
    public static int parseIdentityAge(String idCard) {
        int length = idCard.trim().length();
        if (length == 15) {
            idCard = completionIdCard(idCard);
        } else {
            if (!idCardCheck(idCard))
                return -1;
        }

        // 解析身份证年月日
        int year = Integer.parseInt(idCard.substring(6, 10)),
                month = Integer.parseInt(idCard.substring(10, 12)),
                day = Integer.parseInt(idCard.substring(12, 14));

        // 获取当前东八区日期（以上海时区为准）
        String _current_date = DateTimeUtil.DATE_NO_LINE_FORMATTER.format(DateTimeUtil.getDateTimeWithZone("Asia/Shanghai"));
        int _curr_year = Integer.parseInt(_current_date.substring(0, 4)),
                _curr_month = Integer.parseInt(_current_date.substring(4, 6)),
                _curr_day = Integer.parseInt(_current_date.substring(6, 8));

        // 计算年龄（周岁），精确至天
        if ((_curr_month > month) || (_curr_month == month && _curr_day >= day)) {
            return _curr_year - year;
        } else {
            return _curr_year - (year + 1);
        }
    }

    /**
     * 将15位身份证号码补全至18位
     *
     * @return
     */
    public static String completionIdCard(String idCard15) {
        if (idCard15 == null || idCard15.trim().length() == 0)
            return null;

        if (idCard15.length() != 15)
            return null;

        StringBuilder idCard = new StringBuilder(idCard15);
        idCard.insert(6, "19");

        char[] idCard_number = idCard.toString().toCharArray();
        int sum = 0;
        int i = 0;
        for (; i < 17; i++) {
            sum += (idCard_number[i] - 48) * base_number[i];
        }
        int remainder = sum % 11;

        return idCard.append(check_code[remainder]).toString();
    }
}