package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;

import java.io.File;
import java.util.Map;

/**
 * POI-tl 简单入门（基本用法）
 *
 * @Author Tommy
 * @Date 2025/11/12
 */
public class SimpleTest {

    static Map<String, Object> data() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("title", "Hi, poi-tl Word模板引擎"); // 普通文本
        data.put("link", Texts.of("百度官网链接").link("https://www.baidu.com").create()); // 链接
        data.put("pic", "https://www.baidu.com/img/flexible/logo/pc/result.png"); // 网络图片

        // 列表
        data.put("list1", Lists.newArrayList("黄鹤楼", "岳阳楼", "滕王阁", "鹳雀楼", "蓬莱阁"));
        data.put("list2", Numberings.ofLowerRoman("黄鹤楼", "岳阳楼", "滕王阁", "鹳雀楼", "蓬莱阁").create());

        // 通过Code创建表格
        RowRenderData row0 = Rows.of("景点", "级别").textColor("FFFFFF")
                .bgColor("4472C4").center().create();
        RowRenderData row1 = Rows.create("黄鹤楼", "AAAAA");
        data.put("table", Tables.create(row0, row1));
        return data;
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtil.tempalte("simple-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file).render(data());
        template.writeAndClose(FileUtil.outputStream("hello_world.docx"));
    }
}
