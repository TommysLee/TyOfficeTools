package com.ty.util.office;

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;

/**
 * PDF工具类
 *
 * @Author Tommy
 * @Date 2025/11/15
 */
public class PDFUtil {

    static {
        // License.load_words();
    }

    /**
     * Word文件转PDF文件
     *
     * @param wordPath  Word文件路径
     * @param savePath  文件保存路径
     */
    public static void word2PDF(String wordPath, String savePath) throws Exception {
        Document doc = new Document(wordPath);
        PdfSaveOptions options = new PdfSaveOptions();
        doc.save(savePath, options);
    }
}
