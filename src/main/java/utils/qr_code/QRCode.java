package utils.qr_code;

/**
 * @description: 二维码
 * @author: Fearon
 * @create: 2018-04-16 14:11
 **/
public class QRCode {
    // 宽度
    private Integer width;
    // 高度
    private Integer height;
    // 链接
    private String url;
    // 二维码颜色
    private Integer color;
    // 二维码图像背景色
    private Integer bgColor;
    // 二维码边框
    private Integer margin;
    // 二维码图像格式
    private String type;
    // 数据编码格式(default ---> utf-8)
    private String codeType;

    public QRCode() {
    }

    public QRCode(Integer width, Integer height, String url, Integer color, Integer bgColor,
                  Integer margin, String type, String codeType) {
        this.width = width;
        this.height = height;
        this.url = url;
        this.color = color;
        this.bgColor = bgColor;
        this.margin = margin;
        this.type = type;
        this.codeType = codeType;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getBgColor() {
        return bgColor;
    }

    public void setBgColor(Integer bgColor) {
        this.bgColor = bgColor;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    @Override
    public String toString() {
        return "QRCode [width=" + width + ", heigth=" + height +
                ", url=" + url + ", color=" + color +
                ", bgColor=" + bgColor + ", margin=" + margin +
                ", type=" + type + ", codeType=" + codeType + "]\n";
    }
}
