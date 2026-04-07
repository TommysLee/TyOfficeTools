package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;
import org.ddr.poi.html.HtmlRenderPolicy;

import java.io.File;
import java.util.Map;

/**
 * poi-tl 插入HTML
 *
 * @Author Tommy
 * @Date 2025/11/29
 */
public class HtmlTest {

    static Map<String, Object> data() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("html", "<h3>Hello <u>World</u></h3><p style=\"color: #e32929; white-space: pre-wrap;\">表格    测     试</p><table><tr style=\"font-weight: bold;text-align: center\"><th>姓名</th><th>性别</th></tr><tr><td>张三</td><td>Male</td></tr><tr><td>露西</td><td>女</td></tr></table>");
        data.put("html2", "<p>这是一个包含HTML和LaTeX的混合内容：</p><latex>\\(\\frac{a}{b}\\)</latex>");
        data.put("html3", "<p style=\"white-space: pre-wrap;\">已知圆方程为<latex class=\"ql-formula\">$x^2+y^2+2x-4y+1=0$</latex>，直线<latex class=\"ql-formula\">$2x-y+6=0$</latex>截圆所得的弦长为（      ）</p><p style=\"white-space: pre-wrap;\"><table border='0'><td>A.<latex class=\"ql-formula\">$\\frac{8\\sqrt{5}}{5}$</latex></td><td>B.<latex class=\"ql-formula\">$\\frac{4\\sqrt{5}}{5}$</latex></td><td>C.4</td><td>D.2</td></table></p>");
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
        File file = FileUtil.tempalte("html-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file, config()).render(data());
        template.writeAndClose(FileUtil.outputStream("html.docx"));
    }
}
