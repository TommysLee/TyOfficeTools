package com.ty.excel.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.util.NumberUtil;
import com.ty.util.office.ExcelUtil;
import com.ty.util.FileUtil;

import java.io.File;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jxls-poi Excel生成测试
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
public class ExcelTest {

    static Map<String, Object> data() {
        Map<String, Object> data = Maps.newHashMap();
        data.put(ExcelUtil.COL_HEADING, "空气监测");
        data.put(ExcelUtil.COL_TITLES, Lists.newArrayList("温度", "湿度", "PM2.5"));
        data.put(ExcelUtil.COL_NAMES, Lists.newArrayList("temp", "humi", "pm25"));

        List<Map<String, Object>> itemList = Lists.newArrayList();
        LocalDateTime time = LocalDateTime.of(2025, 11, 11, 0, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (int i = 0; i < 24; i++) {
            Map<String, Object> item = Maps.newHashMap();
            item.put("time", time.format(formatter));
            item.put("temp", NumberUtil.random(270, 290) / 10d);
            item.put("humi", NumberUtil.random(155, 170) / 10d);
            item.put("pm25", NumberUtil.random(200, 300) / 10d);
            itemList.add(item);
            time = time.plusHours(1);
        }
        data.put("data", itemList);
        return data;
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtil.tempalte("excel-template.xlsx");
        String savePath = FileUtil.outputPath("file_out", "") + File.separator + "excel.xlsx";
        File excelFile = ExcelUtil.write(data(), file.getPath(), savePath);
        System.out.println("Excel生成完毕：" + excelFile.getPath());
    }
}
