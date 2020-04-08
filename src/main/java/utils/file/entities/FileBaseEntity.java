package utils.file.entities;

import java.io.File;
import java.io.IOException;

/**
 * @description: 文件管理基础实体
 * @author: Fearon
 * @create: 2019/5/16 18:01
 **/
public class FileBaseEntity {
    // 读写缓存扩容倍数（基本容量为100KB）
    private int capacityMultiple = 1;

    // 文件路径
    private String path;
    // 是否需要删除同名源文件
    private boolean delResFlag = false;

    private File file;

    public FileBaseEntity(String path) {
        this.path = path;
        this.file = createFile(path);
    }

    public int getCapacityMultiple() {
        return capacityMultiple;
    }

    public void setCapacityMultiple(int capacityMultiple) {
        this.capacityMultiple = capacityMultiple;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDeleteResource() {
        return delResFlag;
    }

    public void setDelResFlagFlag(boolean delResFlag) {
        this.delResFlag = delResFlag;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private static File createFile(String path) {
        File file = new File(path);
        File parent = file.getParentFile();
        try {
            if (!parent.exists() && parent.mkdirs() && file.createNewFile())
                System.out.print("file_object creation completed!");

        } catch (IOException e) {
            System.err.print("can not create the file_object!");
            return null;
        }

        return file;
    }
}
