package utils.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.poi.ss.usermodel.Workbook;
import utils.Logger;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * @description: Excel 处理工具
 * @author: Fearon
 * @create: 2019-04-03 10:42
 **/
public class ExportUtil {
    public static void exportExcel(String fileName, Collection list, OutputStream outputStream, String title, List<ExcelExportEntity> colList) {
        try {
            if (list != null) {
                ExportParams params = new ExportParams(title, "数据");
                // params.setType(ExcelType.XSSF);
                // Workbook workbook = ExcelExportUtil.exportExcel(params, colList, list);
                Workbook workbook = ExcelExportUtil.exportBigExcel(params, colList, list);
                ExcelExportUtil.closeExportBigExcel();
                workbook.write(outputStream);
                workbook.close();
                // outputStream.flush();// 刷新流
                outputStream.close();// 关闭流
            }
        } catch (Exception e) {
            Logger.error(ExportUtil.class, "error" + e);
        }
    }
}