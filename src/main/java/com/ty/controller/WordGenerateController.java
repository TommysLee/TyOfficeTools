package com.ty.controller;

import com.ty.constant.MIME;
import com.ty.entity.Data;
import com.ty.service.DataServcie;
import com.ty.util.FileUtil;
import com.ty.util.office.WordUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Map;

/**
 * Word生成Controller
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
@Controller
@RequestMapping("/gen/word")
public class WordGenerateController extends BaseController {

    @Autowired
    private DataServcie dataServcie;

    /**
     * Word入门示例
     */
    @RequestMapping("/simple")
    public void simple(Data data, HttpServletResponse response) throws Exception {
        File templateFile = FileUtil.tempalte("simple-template.docx");
        String savePath = FileUtil.outputPath("simple.docx");
        Map<String, Object> dataMap = dataServcie.example_simple(data);

        this.generate(dataMap, templateFile, savePath, MIME.WORD, "Word入门示例.docx", response);
    }

    /**
     * Word表格高级用法
     */
    @RequestMapping("/table")
    public void table(Data data, HttpServletResponse response) throws Exception {
        File templateFile = FileUtil.tempalte("table-template.docx");
        String savePath = FileUtil.outputPath("table.docx");
        Map<String, Object> dataMap = dataServcie.example_table(data);

        this.generate(dataMap, templateFile, savePath, MIME.WORD, "Word表格高级用法.docx", response);
    }

    /**
     * Word双栏
     */
    @RequestMapping("/column")
    public void column(Data data, HttpServletResponse response) throws Exception {
        File templateFile = FileUtil.tempalte("column-template.docx");
        String savePath = FileUtil.outputPath("column.docx");
        Map<String, Object> dataMap = dataServcie.example_column(data);

        this.generate(dataMap, templateFile, savePath, MIME.WORD, "Word双栏.docx", response);
    }

    /**
     * Word Chart图表
     */
    @RequestMapping("/chart")
    public void chart(HttpServletResponse response) throws Exception {
        File templateFile = FileUtil.tempalte("chart-template.docx");
        String savePath = FileUtil.outputPath("chart.docx");
        Map<String, Object> dataMap = dataServcie.example_chart();

        this.generate(dataMap, templateFile, savePath, MIME.WORD, "Word_Chart图表.docx", response);
    }

    /*
     * 生成Word文件并下载
     */
    void generate(Map<String, Object> dataMap, File templateFile, String savePath, String mime, String fileName, HttpServletResponse response) throws Exception {
        File wordFile = WordUtil.write(dataMap, templateFile, savePath);
        this.download(response, fileName, mime, wordFile);
    }
}
