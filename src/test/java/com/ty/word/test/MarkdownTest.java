package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.markdown.MarkdownRenderData;
import com.deepoove.poi.plugin.markdown.MarkdownRenderPolicy;
import com.deepoove.poi.plugin.markdown.MarkdownStyle;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * poi-tl Markdown 测试
 *
 * @Author Tommy
 * @Date 2025/11/29
 */
public class MarkdownTest {

    static Map<String, Object> data() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("README.md")));
        // String content = "这是一个公式：$$E=mc^2$$";
        System.out.println(content);

        MarkdownRenderData code = new MarkdownRenderData();
        code.setMarkdown(content);
        code.setStyle(MarkdownStyle.newStyle());

        Map<String, Object> data = Maps.newHashMap();
        data.put("md", code);
        return data;
    }

    static Configure config() {
        ConfigureBuilder builder = Configure.builder();
        builder.bind("md", new MarkdownRenderPolicy());
        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtil.tempalte("markdown-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file, config()).render(data());
        template.writeAndClose(FileUtil.outputStream("markdown.docx"));
    }
}
