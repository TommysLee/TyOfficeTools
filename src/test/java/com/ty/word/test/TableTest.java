package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.entity.Place;
import com.ty.util.FileUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * POI-tl表格高级用法：表格行循环
 *
 * @Author Tommy
 * @Date 2025/11/12
 */
public class TableTest {

    static Map<String, Object> data() throws Exception {
        Map<String, Object> data = Maps.newHashMap();
        data.put("title", "LoopRowTableRenderPolicy 是一个特定场景的插件，根据集合数据循环表格行！");

        // 表格数据1
        List<Place> list3 = Lists.newArrayList();
        list3.add(new Place().setName("蓬莱阁").setLevel("AAAA"));
        list3.add(new Place().setName("鹳雀楼").setLevel("AAAA"));
        data.put("list3", list3);
        data.put("show", true);

        // 表格数据2
        List<Place> list4 = Lists.newArrayList();
        list4.add(new Place().setName("蓬莱阁").setLevel("AAAA"));
        list4.add(new Place().setName("黄鹤楼").setLevel("AAAAA"));
        Map<String, Object> subMap = Maps.newHashMap();
        subMap.put("tables", list4);
        data.put("list4", subMap);

        // 表格数据3
        List<Place> list = Lists.newArrayList();
        list.add(new Place().setName("蓬莱阁").setLevel("AAAA"));
        list.add(new Place().setName("鹳雀楼").setLevel("AAAAA"));
        data.put("list", list);

        return data;
    }

    static Configure config() {
        /*
         * 插件，又称为自定义函数，它允许用户在模板标签位置处执行预先定义好的函数。
         */

        // 插件：表格行循环
        LoopRowTableRenderPolicy loopRowTableRenderPolicy = new LoopRowTableRenderPolicy();

        // 配置构建器
        ConfigureBuilder builder = Configure.builder();

        // 单独配置每个标签的策略插件
        builder.bind("list3", loopRowTableRenderPolicy);
        builder.bind("tables", loopRowTableRenderPolicy);

        // 将插件注册为新标签类型（简化配置，不用像上面一样，配置每个标签/属性的策略）
        // 此时，{{%var}} 将成为一种新的标签类型，它的执行函数是LoopRowTableRenderPolicy
        builder.addPlugin('%', loopRowTableRenderPolicy);

        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtil.tempalte("table-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file, config()).render(data());
        template.writeAndClose(FileUtil.outputStream("table.docx"));
    }
}
