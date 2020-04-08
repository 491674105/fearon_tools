package utils.file;

import java.nio.channels.FileChannel;

/**
 * @description: 文件读取模块
 * @author: Fearon
 * @create: 2019/5/16 17:58
 **/
public interface FileReader {
    byte[] reader(FileChannel readerChannel);
}
