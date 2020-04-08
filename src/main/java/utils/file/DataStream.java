package utils.file;

import java.nio.channels.FileChannel;

/**
 * @description: 数据流管理模块
 * @author: Fearon
 * @create: 2019/5/20 11:21
 **/
public interface DataStream {
    FileChannel openReader();
    boolean closeReader(FileChannel readerChannel);

    FileChannel openWriter();
    boolean closeWriter(FileChannel writerChannel);
}
