package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.Pictures;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;

import java.io.File;
import java.util.Map;
import java.util.Random;

/**
 * POI-tl Chart图表测试
 *
 * @Author Tommy
 * @Date 2025/11/12
 */
public class ChartTest {

    static Map<String, Object> data() {
        Map<String, Object> data = Maps.newHashMap();
        // 图片
        data.put("img", Pictures.ofUrl("https://www.baidu.com/img/flexible/logo/pc/result.png").create());

        // 图表数据
        String[] categories = new String[] { "Q1", "Q2", "Q3", "Q4" };
        String sname = "销售额";
        Double[] sdata = new Double[] { 1500000.0,  3000000.0, 4200000.0, 6800000.0};
        ChartMultiSeriesRenderData barData = Charts.ofBar("柱状图(销售报表)", categories)
                .addSeries(sname, sdata)
                .addSeries("去年同期", sdata)
                .create();
        data.put("barChart", barData);

        ChartMultiSeriesRenderData lineData = Charts.ofLine("折线图(销售报表)", categories)
                .addSeries(sname, sdata)
                .create();
        data.put("lineChart", lineData);

        ChartMultiSeriesRenderData d3BarData = Charts.ofBar3D("折线图(销售报表)", categories)
                .addSeries(sname, sdata)
                .create();
        data.put("d3BarChart", d3BarData);

        ChartSingleSeriesRenderData d3PieData = Charts.ofSingleSeries ("饼图(销售报表)", categories)
                .series(sname, sdata)
                .create();
        data.put("d3PieChart", d3PieData);

        int len = 31;
        String[] categoriyArr = new String[len];
        Double[] sdataArr = new Double[len];
        for (int i = 1; i <= len; i++) {
            categoriyArr[i - 1] = i + "日";
            sdataArr[i - 1] = Math.floor(new Random().nextDouble() * 100);
        }
        ChartMultiSeriesRenderData charData = Charts.ofBar ("10月销售报表", categoriyArr)
                .addSeries(sname, sdataArr)
                .create();
        data.put("charData", charData);
        data.put("show", true);
        data.put("categories", Lists.newArrayList(categoriyArr));
        return data;
    }

    static Configure config() {
        ConfigureBuilder builder = Configure.builder();
        builder.useSpringEL();
        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtil.tempalte("chart-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file, config()).render(data());
        template.writeAndClose(FileUtil.outputStream("chart.docx"));
    }
}
