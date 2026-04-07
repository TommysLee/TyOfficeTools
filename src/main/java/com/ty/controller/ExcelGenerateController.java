package com.ty.controller;

import com.ty.constant.MIME;
import com.ty.entity.Data;
import com.ty.service.DataServcie;
import com.ty.util.FileUtil;
import com.ty.util.office.ExcelUtil;
import com.ty.util.office.WordUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Map;

/**
 * Excel生成Controller
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
@Controller
@RequestMapping("/gen/excel")
public class ExcelGenerateController extends BaseController {

    @Autowired
    private DataServcie dataServcie;

    @RequestMapping("/table")
    public void table(Data data, HttpServletResponse response) throws Exception {
        File templateFile = FileUtil.tempalte("excel-template.xlsx");
        String savePath = FileUtil.outputPath("public", "") + File.separator + "excel.xlsx";
        Map<String, Object> dataMap = dataServcie.example_excel();

        this.generate(dataMap, templateFile, savePath, MIME.EXCEL, "空气监测.xlsx", response);
    }

    /*
     * 生成Excel文件并下载
     */
    void generate(Map<String, Object> dataMap, File templateFile, String savePath, String mime, String fileName, HttpServletResponse response) throws Exception {
        File excelFile = ExcelUtil.write(dataMap, templateFile.getPath(), savePath);
        this.download(response, fileName, mime, excelFile);
    }
}
