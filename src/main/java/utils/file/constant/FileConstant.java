package utils.file.constant;

/**
 * @description: 文件工具相关常量
 * @author: Fearon
 * @create: 2019/5/16 17:59
 **/
public class FileConstant {
    // 默认缓存容量(100B)
    public static final int DEFAULT_CAPACITY = 102400;

    // 单个文件存储上限（20M 为严格控制在20M以内 写入字符不能超过20W）
    public static final long MAX_SIZE = 20000000L;
}
