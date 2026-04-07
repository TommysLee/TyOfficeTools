package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;
import com.ty.util.HtmlUtil;
import org.ddr.poi.html.HtmlRenderPolicy;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * poi-tl 插入HTML
 *
 * @Author Tommy
 * @Date 2025/11/29
 */
public class HtmlTest2 {

    static Map<String, Object> data() throws Exception {
        List<Map<String, String>> queList = Lists.newArrayList();

        // 读取目录qdb下的所有TXT内容
        List<String> textList = FileUtil.readFiles("qdb");
        for (String content : textList) {
            Map<String, String> que = Maps.newHashMap();
            que.put("content", HtmlUtil.resolve(content));
            queList.add(que);
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("queList", queList);
        return data;
    }

    static Configure config() {
        // 插件：扩展插件 支持HTML渲染 HtmlRenderPolicy
        HtmlRenderPolicy htmlRenderPolicy = new HtmlRenderPolicy();

        // 配置构建器
        ConfigureBuilder builder = Configure.builder();

        // 将插件注册为新标签类型
        // 此时，{{&var}} 将成为一种新的标签类型，HtmlRenderPolicy
        builder.addPlugin('&', htmlRenderPolicy);

        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtil.tempalte("html-latex-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file, config()).render(data());
        template.writeAndClose(FileUtil.outputStream("综合题集.docx"));
    }
}
