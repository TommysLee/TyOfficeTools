package com.ty.pdf.test;

import com.ty.util.FileUtil;
import com.ty.util.office.PDFUtil;

import java.io.File;

/**
 * aspose.words Word转PDF
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
public class Word2PDFTest {
    static final String PATH = FileUtil.outputPath("file_out", "").substring(1) + File.separator;

    public static void main(String[] args) throws Exception {
        // String wordPath = Word2PDFTest.class.getClassLoader().getResource("example/report.docx").getFile().substring(1);
        String wordPath = PATH + "column.docx";
        String savePath = PATH + "output.pdf";

        long begin = System.currentTimeMillis();

        PDFUtil.word2PDF(wordPath, savePath);

        long end = System.currentTimeMillis();

        System.out.println("Word转PDF耗时：" + (end - begin) + "ms.");
        System.out.println("PDF文件保存至：" + savePath);
    }
}
