package utils;

import utils.string.StringUtil;
import utils.time.DateTimeUtil;

import java.time.LocalDateTime;

public class Logger {
    public static <T> void error(Class<T> clazz, String message){
        System.err.print(StringUtil.contact(
                DateTimeUtil.DATETIME_FORMATTER.format(LocalDateTime.now()) +
                        " [" +
                        Thread.currentThread().getName() +
                        "] " +
                        "ERROR  " +
                        clazz.getName() +
                        " - " +
                        message +
                        "\n"
        ));
    }
}
