package com.ty.controller;

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;
import com.ty.constant.MIME;
import com.ty.entity.Data;
import com.ty.service.DataServcie;
import com.ty.util.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * PDF生成Controller
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
@Controller
@RequestMapping("/gen/pdf")
public class PDFGenerateController extends BaseController {

    @Autowired
    private DataServcie dataServcie;

    @RequestMapping("/word")
    public void word(Data data, HttpServletResponse response) throws Exception {
        String dirPath = FileUtil.outputPath("public", "") + File.separator;
        dirPath = dirPath.substring(1);

        Document doc = new Document(dirPath + "chart.docx");
        PdfSaveOptions options = new PdfSaveOptions();
        doc.save(dirPath + "chart.pdf", options);

        this.download(response, "Chart报表.pdf", MIME.PDF, new File(dirPath + "chart.pdf"));
    }
}
