package com.ty.service;

import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.entity.Data;
import com.ty.entity.Place;
import com.ty.entity.Que;
import com.ty.util.FileUtil;
import com.ty.util.HtmlUtil;
import com.ty.util.NumberUtil;
import com.ty.util.office.ExcelUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Example数据业务逻辑层
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
@Service
public class DataServcie {

    /**
     * 获取Word入门示例的数据
     *
     * @param data 前端传递的数据
     * @return Map<String, Object>
     */
    public Map<String, Object> example_simple(Data data) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("title", data.getTitle()); // 普通文本
        dataMap.put("link", Texts.of(data.getLinkText()).link(data.getLink()).create()); // 链接
        dataMap.put("pic", data.getPic()); // 网络图片
        dataMap.put("list2", Numberings.ofDecimal(data.getList().toArray(new String[0])).create()); // 列表

        // 通过Code创建表格
        RowRenderData[] rows = new RowRenderData[2];
        rows[0] = Rows.of(data.getCols().toArray(new String[0])).textColor("FFFFFF")
                .bgColor("4472C4").center().create();
        rows[1] = Rows.create(data.getRows().toArray(new String[0]));
        dataMap.put("table", Tables.create(rows));
        return dataMap;
    }

    /**
     * 获取Word表格高级用法的示例数据
     *
     * @param data 前端传递的数据
     * @return Map<String, Object>
     */
    public Map<String, Object> example_table(Data data) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("title", data.getTitle()); // 普通文本
        dataMap.put("show", false);

        // 表格数据
        List<Place> list = Lists.newArrayList();
        for (String item : data.getRows()) {
            list.add(new Place().setName(item).setLevel("AAAA"));
        }
        dataMap.put("list", list);
        return dataMap;
    }

    /**
     * 获取Word双栏的示例数据
     *
     * @param data 前端传递的数据
     * @return Map<String, Object>
     */
    public Map<String, Object> example_column(Data data) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("title", data.getTitle()); // 普通文本
        dataMap.put("author", "岳飞");

        String content = """
                怒发冲冠，凭栏处、潇潇雨歇。
                抬望眼，仰天长啸，壮怀激烈。
                三十功名尘与土，八千里路云和月。
                莫等闲、白了少年头，空悲切。
                靖康耻，犹未雪。
                臣子恨，何时灭。
                驾长车，踏破贺兰山缺。
                壮志饥餐胡虏肉，笑谈渴饮匈奴血。
                待从头、收拾旧山河，朝天阙。""";
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            contentBuilder.append(content).append("\n\n");
        }
        dataMap.put("content", contentBuilder.toString());
        return dataMap;
    }

    /**
     * 获取Word Chart图表的示例数据
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> example_chart() {
        Map<String, Object> dataMap = Maps.newHashMap();
        // 图片
        dataMap.put("img", Pictures.ofUrl("https://www.baidu.com/img/flexible/logo/pc/result.png").create());

        // 图表数据
        String[] categories = new String[] { "Q1", "Q2", "Q3", "Q4" };
        String sname = "销售额";
        Double[] sdata = new Double[] { 1500000.0,  3000000.0, 4200000.0, 6800000.0};
        ChartMultiSeriesRenderData barData = Charts.ofBar("柱状图(销售报表)", categories)
                .addSeries(sname, sdata)
                .addSeries("去年同期", sdata)
                .create();
        dataMap.put("barChart", barData);

        ChartMultiSeriesRenderData lineData = Charts.ofLine("折线图(销售报表)", categories)
                .addSeries(sname, sdata)
                .create();
        dataMap.put("lineChart", lineData);

        ChartMultiSeriesRenderData d3BarData = Charts.ofBar3D("折线图(销售报表)", categories)
                .addSeries(sname, sdata)
                .create();
        dataMap.put("d3BarChart", d3BarData);

        ChartSingleSeriesRenderData d3PieData = Charts.ofSingleSeries ("饼图(销售报表)", categories)
                .series(sname, sdata)
                .create();
        dataMap.put("d3PieChart", d3PieData);

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
        dataMap.put("charData", charData);
        dataMap.put("show", true);
        return dataMap;
    }

    /**
     * 获取Excel示例数据
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> example_excel() {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put(ExcelUtil.COL_HEADING, "空气监测");
        dataMap.put(ExcelUtil.COL_TITLES, Lists.newArrayList("温度", "湿度", "PM2.5"));
        dataMap.put(ExcelUtil.COL_NAMES, Lists.newArrayList("temp", "humi", "pm25"));

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
        dataMap.put("data", itemList);
        return dataMap;
    }

    /**
     * 读取富文本编辑器示例数据集
     */
    public List<Que> queDataList() throws Exception {
        List<Que> dataList = Lists.newArrayList();
        // 读取目录qdb下的所有TXT内容
        List<String> textList = FileUtil.readFiles("qdb");
        int tick = 1;
        for (String content : textList) {
            dataList.add(new Que().setId(String.valueOf(tick++)).setHtml(content));
        }
        return dataList;
    }
}
