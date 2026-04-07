package com.ty.word.test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.toc.TOCRenderPolicy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.util.FileUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * POI-tl 分栏测试
 *
 * @Author Tommy
 * @Date 2025/11/12
 */
public class ColumnTest {

    static Map<String, Object> data() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("title", "满江红");
        data.put("author", "岳飞");
        String content = """
                怒发冲冠，凭栏处、潇潇雨歇。
                抬望眼，仰天长啸，壮怀激烈。
                三十功名尘与土，八千里路云和月。
                莫等闲、白了少年头，空悲切。
                靖康耻，犹未雪。
                臣子恨，何时灭。
                驾长车，踏破贺兰山缺。
                壮志饥餐胡虏肉，笑谈渴饮匈奴血。
                待从头、收拾旧山河，朝天阙。""";
        StringBuilder contentBuilder = new StringBuilder();
        List<String> categories = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            contentBuilder.append(content).append("\n\n");
            categories.add("第" + (i+1) + "章");
        }
        data.put("content", contentBuilder.toString());
        data.put("categories", categories);
        return data;
    }

    static void updateFields() throws Exception {
        try (FileInputStream fis = new FileInputStream("column.docx")) {
            XWPFDocument doc = new XWPFDocument(fis);
            // 更新所有域字段（含目录）
            doc.enforceUpdateFields();

            // 保存更新后的文档
            try (FileOutputStream fos = new FileOutputStream("column_updated.docx")) {
                doc.write(fos);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configure config = Configure.builder()
                .bind("toc", new TOCRenderPolicy()) // 标签名需与模板一致
                .build();

        File file = FileUtil.tempalte("column-template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file, config).render(data());
        template.writeAndClose(FileUtil.outputStream("column.docx"));
    }
}
