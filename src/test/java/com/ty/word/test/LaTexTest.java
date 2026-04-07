package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;
import org.ddr.poi.html.HtmlRenderPolicy;
import org.ddr.poi.latex.LaTeXRenderPolicy;
import org.ddr.poi.latex.LaTeXUtils;
import uk.ac.ed.ph.snuggletex.SnuggleSession;

import java.io.File;
import java.util.Map;

/**
 * poi-tl 插入LaTex公式
 *
 * @Author Tommy
 * @Date 2025/11/29
 */
public class LaTexTest {

    static Map<String, Object> data() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("latex1", "$\\frac{a}{b}$");
        data.put("latex2", "\\(({x+a})^{2}=\\sum ^{n}_{k=0} (^{1}_{2}){x}^{k}{a}^{n-k}\\)");
        data.put("latex3", "\\(f(x)=\\frac{a}{x} +\\ln{x} \\left ( a\\in R \\right ) \\)");
        data.put("latex4", "$\\begin{array}{l}     \\text{对于方程形如：}x^{3}-1 = 0 \\\\    \\text{设}\\text{:}\\omega = \\frac{-1+\\sqrt{3}i}{2} \\\\    x_{1} = 1,x_{2} = \\omega = \\frac{-1+\\sqrt{3}i}{2} \\\\    x_{3} = \\omega ^{2} = \\frac{-1-\\sqrt{3}i}{2}  \\end{array}$");
        return data;
    }

    static Configure config() {
        // 插件：扩展插件 支持HTML渲染 HtmlRenderPolicy
        LaTeXRenderPolicy laTeXRenderPolicy = new LaTeXRenderPolicy();

        // 配置构建器
        ConfigureBuilder builder = Configure.builder();

        // 将插件注册为新标签类型
        // 此时，{{&var}} 将成为一种新的标签类型，HtmlRenderPolicy
        builder.addPlugin('&', laTeXRenderPolicy);

        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtil.tempalte("latex-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file, config()).render(data());
        template.writeAndClose(FileUtil.outputStream("latex.docx"));
    }
}
