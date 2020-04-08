package utils.file.impl;


import utils.Logger;
import utils.file.DataStream;
import utils.file.entities.FileBaseEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @description: 数据流管理模块基本实现
 * @author: Fearon
 * @create: 2019/5/20 11:22
 **/
public class DataBaseStream implements DataStream {
    private FileBaseEntity fileBaseEntity;

    public DataBaseStream(FileBaseEntity fileBaseEntity) {
        this.fileBaseEntity = fileBaseEntity;
    }

    @Override
    public FileChannel openReader() {
        FileInputStream fileInputStream = null;
        FileChannel channel = null;
        try {
            fileInputStream = new FileInputStream(fileBaseEntity.getFile());
            channel = fileInputStream.getChannel();
        } catch (FileNotFoundException e) {
            Logger.error(this.getClass(), "can not open the channel!");
        }
        return channel;
    }

    @Override
    public boolean closeReader(FileChannel readerChannel) {
        try {
            if (null != readerChannel) {
                readerChannel.close();
            }
        } catch (IOException e) {
            Logger.error(this.getClass(), "can not close the channel!");
            return false;
        }
        return true;
    }

    @Override
    public FileChannel openWriter() {
        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        try {
            fileOutputStream = new FileOutputStream(fileBaseEntity.getFile());
            channel = fileOutputStream.getChannel();
        } catch (FileNotFoundException e) {
            Logger.error(this.getClass(), "can not open the channel!");
        }
        return channel;
    }

    @Override
    public boolean closeWriter(FileChannel writerChannel) {
        try {
            if (null != writerChannel) {
                writerChannel.close();
            }
        } catch (IOException e) {
            Logger.error(this.getClass(), "can not close the channel!");
            return false;
        }
        return true;
    }
}
