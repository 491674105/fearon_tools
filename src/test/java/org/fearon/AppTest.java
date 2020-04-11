package org.fearon;

import static org.junit.Assert.assertTrue;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.junit.Test;
import utils.Logger;
import utils.poi.ExportUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) {
        BufferedReader sales_reader = null;
        BufferedReader pro_info_reader = null;
        try {
            sales_reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File("C:\\Users\\Administrator\\Desktop\\mhts_goods_sales.tsv")
                            ),
                            StandardCharsets.UTF_8
                    )
            );

            pro_info_reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File("C:\\Users\\Administrator\\Desktop\\pro_ids.tsv")
                            ),
                            StandardCharsets.UTF_8
                    )
            );
        } catch (FileNotFoundException e) {
            Logger.error(App.class, "文件不存在！" + Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }

        List<ExcelExportEntity> header = new ArrayList<>();
        List<Map<String, Object>> row = new ArrayList<>();
        Map<String, Object> menu = new HashMap<>();
        try {
            String line = null;
            int line_index = 0;
            while ((line = sales_reader.readLine()) != null) {
                System.out.print("第" + (++line_index) + "行开始处理 ---> ");

                String[] segment_out = line.trim().split("\t");
                ExcelExportEntity entity = null;
                Map<String, Object> division = new HashMap<>();
                division.put("mht", segment_out[0]);
                division.put("emp", segment_out[1]);
                division.put("org", segment_out[3]);
                division.put("addr", segment_out[4]);
                division.put("owner", segment_out[5]);
                division.put("phone", segment_out[6]);
                // 表头操作
                if (line_index == 1) {
                    entity = new ExcelExportEntity("终端名称", "mht");
                    header.add(entity);
                    entity = new ExcelExportEntity("网点归属人", "emp");
                    header.add(entity);
                    entity = new ExcelExportEntity("所属架构", "org");
                    header.add(entity);
                    entity = new ExcelExportEntity("地址", "addr");
                    header.add(entity);
                    entity = new ExcelExportEntity("联系人", "owner");
                    header.add(entity);
                    entity = new ExcelExportEntity("联系电话", "phone");
                    header.add(entity);
                    String line_in = null;
                    while ((line_in = pro_info_reader.readLine()) != null) {
                        String[] pro_info = line_in.trim().split("\t");
                        entity = new ExcelExportEntity(pro_info[1], pro_info[0]);
                        header.add(entity);
                        menu.put(pro_info[0], pro_info[1]);
                    }
                }


                for (Map.Entry<String, Object> entry : menu.entrySet()) {
                    division.put(entry.getKey(), 0);
                }

                String[] segment_in = segment_out[2].trim().split(",");
                for (String segment : segment_in) {
                    String[] pro_info = segment.trim().split("-");
                    division.put(pro_info[0], pro_info[2]);
                }
                row.add(division);

                System.out.println("处理完成！");
            }
            sales_reader.close();
        } catch (IOException e) {
            Logger.error(App.class, "文件不存在！" + Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }

        try {
            OutputStream output = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\交货商品统计.xlsx"));
            ExportUtil.exportExcel("交货商品统计", row, output, "交货商品统计", header);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
