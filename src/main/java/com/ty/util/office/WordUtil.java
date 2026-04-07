package com.ty.util.office;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.ddr.poi.html.HtmlRenderPolicy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Word文件生成工具类
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
@Slf4j
public class WordUtil {

    public static final Configure configure;

    static {
        ConfigureBuilder builder = Configure.builder();

        // 将插件注册为新标签类型（简化配置，不用像上面一样，配置每个标签/属性的策略）
        // 此时，{{%var}} 将成为一种新的标签类型，它的执行函数是LoopRowTableRenderPolicy
        builder.addPlugin('%', new LoopRowTableRenderPolicy());
        builder.addPlugin('&', new HtmlRenderPolicy()); // 渲染HTML与LaTex

        configure = builder.build();
    }

    /**
     * 生成Word文件
     *
     * @param data          数据
     * @param templatePath  模板路径
     * @param savePath      文件保存路径
     * @return file 返回生成的文件对象
     */
    public static File write(Map<String, Object> data, String templatePath, String savePath) throws Exception {
        if (isValid(templatePath)) {
            return write(data, new File(templatePath), savePath);
        }
        return null;
    }

    /**
     * 生成Word文件
     *
     * @param data          数据
     * @param templateFile  模板文件
     * @param savePath      文件保存路径
     * @return file 返回生成的文件对象
     */
    public static File write(Map<String, Object> data, File templateFile, String savePath) throws Exception {
        File newFile = null;
        if (exists(templateFile) && isValid(savePath)) {
            XWPFTemplate template = XWPFTemplate.compile(templateFile, configure).render(data);
            newFile = createFile(savePath);
            template.writeAndClose(new FileOutputStream(newFile));
        }
        return newFile;
    }

    /**
     * 创建File对象
     *
     * @param path 文件路径
     * @return FileOutputStream
     */
    public static File createFile(String path) {
        File file = null;
        if (StringUtils.isNotBlank(path)) {
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    /**
     * 判断是否为有效的Word文件
     *
     * @param fileName Word文件名或含文件名的路径
     * @return boolean
     */
    public static boolean isValid(String fileName) {
        boolean flag = StringUtils.isNotBlank(fileName) && FilenameUtils.getExtension(fileName).matches("(?i)docx");
        if (!flag) {
            log.warn("不是一个有效的Word文件：" + fileName);
        }
        return flag;
    }

    /**
     * 检查文件是否存在
     *
     * @param file 文件对象
     * @return boolean
     */
    public static boolean exists(File file) {
        boolean flag = false;
        if (null != file && file.exists() && file.isFile()) {
            flag = true;
        } else {
            log.warn("文件不存在或非文件：{}", (null != file? file.getPath() : null));
        }
        return flag;
    }
}
