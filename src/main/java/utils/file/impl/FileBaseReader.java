package utils.file.impl;


import utils.Logger;
import utils.file.FileReader;
import utils.file.constant.FileConstant;
import utils.file.entities.FileBaseEntity;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 文件读取基本实现
 * @author: Fearon
 * @create: 2019/5/17 9:14
 **/
public class FileBaseReader implements FileReader {
    private FileBaseEntity fileBaseEntity;

    public FileBaseReader(FileBaseEntity fileBaseEntity) {
        this.fileBaseEntity = fileBaseEntity;
    }

    @Override
    public byte[] reader(FileChannel readerChannel) {
        byte[] data = null;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(FileConstant.DEFAULT_CAPACITY * fileBaseEntity.getCapacityMultiple());

            byte[] cache = null;
            int length = readerChannel.read(buffer), cacheLength = 0;
            for (; length != -1; length = readerChannel.read(buffer)) {
                buffer.clear();
                if (null == data) {
                    data = buffer.array();
                } else {
                    cache = data;
                    cacheLength = cache.length;
                    data = new byte[length + cacheLength];
                    System.arraycopy(cache, 0, data, 0, cacheLength);
                    System.arraycopy(buffer.array(), 0, data, cacheLength, length);
                }
            }
        } catch (IOException e) {
            Logger.error(this.getClass(), "can not read the file data!");
        }

        return data;
    }
}
