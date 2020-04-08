package utils.qr_code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * @description: 二维码工具类
 * @author: Fearon
 * @create: 2018-04-16 14:04
 **/
public class QRCodeTool {
    /**
     * 默认二维码颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 默认二维码图像背景色
     */
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 默认二维码边框
     */
    private static final int MARGIN = 1;
    /**
     * 二维码图像格式
     */
    private static final String FORMAT = "png";

    /**
     * 二维码数据流生成
     *
     * @param url    需要封装的完整url
     * @param code   二维码信息对象
     * @param output 数据流输出
     */
    public static boolean QRCodeCreator(String url, QRCode code, OutputStream output) {
        Hashtable<EncodeHintType, Object> hint = new Hashtable<>();
        String code_type = code.getCodeType();
        if (null == code_type || code_type.length() <= 0)
            hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        else
            hint.put(EncodeHintType.CHARACTER_SET, code.getCodeType());

        if (null == code.getMargin())
            hint.put(EncodeHintType.MARGIN, MARGIN);
        else
            hint.put(EncodeHintType.MARGIN, code.getMargin());

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,
                    (code.getWidth() == null ? 200 : code.getWidth()), (code.getHeight() == null ? 200 : code.getHeight()), hint);

            BufferedImage image = toImage(matrix);

            return ImageIO.write(image, FORMAT, output);
        } catch (WriterException e) {
            System.err.println("二维码生成失败！" + (Object[]) e.getStackTrace());
            return false;
        } catch (IOException e) {
            System.err.println("数据流异常！" + (Object[]) e.getStackTrace());
            return false;
        }
    }

    public static BufferedImage toImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);

        return image;
    }
}
