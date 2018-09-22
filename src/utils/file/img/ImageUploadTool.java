package utils.file.img;

public class ImageUploadTool {
    /**
     * createTime 2017/2/28
     * Description: 限制图片格式
     * @param fileName 图片名称
     * @return boolean
     */
    public static boolean limitImgFormat(String fileName) {
        String[] le = CommonVariable.IMGFORMAT.split("\\|");
        for (String len : le) {
            if (len.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * createTime 2017/2/28
     * Description: 限制图片大小
     * @param size 图片大小
     * @return boolean
     */
    public static boolean limitImgSize(Long size) {

        Long maxsize = (long) (CommonVariable.MAXIMGSIZE * 1024*1024);
        if (size > maxsize) {
            return false;
        }
        return true;
    }
}
