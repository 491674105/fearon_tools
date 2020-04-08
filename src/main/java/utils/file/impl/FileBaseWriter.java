package utils.file.impl;

import utils.Logger;
import utils.file.FileWriter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 文件写入基本实现
 * @author: Fearon
 * @create: 2019/5/17 11:30
 **/
public class FileBaseWriter implements FileWriter {
    @Override
    public void writer(byte[] data, FileChannel writerChannel) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(data, 0, data.length);
            int length = writerChannel.write(buffer);
            for (; length != 0; length = writerChannel.write(buffer)) {
                System.out.print(length + " bytes has been written!");
            }

        } catch (IOException e) {
            Logger.error(this.getClass(), "can not write the file data!");
        }
    }
}
