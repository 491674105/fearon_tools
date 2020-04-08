package utils.file;

import java.nio.channels.FileChannel;

/**
 * @description: 文件写入模块
 * @author: Fearon
 * @create: 2019/5/16 17:59
 **/
public interface FileWriter {
    void writer(byte[] data, FileChannel writerChannel);
}
