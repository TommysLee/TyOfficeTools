package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;
import org.ddr.poi.html.HtmlRenderPolicy;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * poi-tl 渲染数学题目
 *
 * @Author Tommy
 * @Date 2025/11/29
 */
public class HtmlLaTexTest {

    static Map<String, Object> data() {
        String content = "<p>已知角α终边上一点P(-4,3)，&nbsp;</p>";
        content += "<p>（1）求 sina，cosa 的值&nbsp;</p>";
        content += "<p>（2）求<latex>$\\dfrac{cos(\\dfrac{π}{2}+α)sin(-π-α)}{cos(\\dfrac{11π}{2}-α)sin(\\dfrac{9π}{2}+α)}$</latex>的值．</p>";

        Map<String, String> que = Maps.newHashMap();
        que.put("content", content);

        List<Map<String, String>> queList = Lists.newArrayListWithCapacity(100);
        for (int i = 0; i < 100; i++) {
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
        template.writeAndClose(FileUtil.outputStream("html-latex.docx"));
    }
}
