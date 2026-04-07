package com.ty.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * File工具类
 *
 * @Author Tommy
 * @Date 2025/11/12
 */
@Slf4j
public class FileUtil {

    /**
     * 获取模板文件
     *
     * @param templateName 模板名称
     * @return File
     */
    public static File tempalte(String templateName) {
        String path = FileUtil.class.getClassLoader().getResource("file_templates/" + templateName).getFile();
        return new File(path);
    }

    /**
     * 获取文件输出流对象
     *
     * @param fileName 文件名称
     * @return FileOutputStream
     */
    public static FileOutputStream outputStream(String fileName) throws Exception {
        String path = outputPath("file_out", "");
        path += File.separator + fileName;
        System.out.println("输出文件：" + path);

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return new FileOutputStream(file);
    }

    /**
     * 获取输出路径
     *
     * @param fileName 文件名称
     * @return String
     */
    public static String outputPath(String fileName) {
        String path = outputPath(fileName, "public");
        log.info("保存路径 {}", path);
        return path;
    }

    /**
     * 获取输出路径
     *
     * @param fileName 文件名称
     * @param target   目录名称
     * @return String
     */
    public static String outputPath(String fileName, String target) {
        String path = FileUtil.class.getClassLoader().getResource(target).getFile();
        if (StringUtils.isNotBlank(fileName)) {
            path += File.separator + fileName;
        }
        return path;
    }

    /**
     * 读取目录及子目录中的所有TXT文件内容
     *
     * @param dirPath 目录
     * @return List<String>
     * @throws IOException
     */
    public static List<String> readFiles(String dirPath) throws IOException {
        dirPath = outputPath(null, dirPath).substring(1);

        Collection<File> txtFiles = FileUtils.listFiles(new File(dirPath), new String[] {"txt"}, true);
        List<String> textList = Lists.newArrayListWithCapacity(txtFiles.size());
        Pattern pattern = Pattern.compile("\\d+");
        txtFiles.stream().sorted((f1, f2) ->  {
            String name1 = FilenameUtils.getBaseName(f1.getName());
            String name2 = FilenameUtils.getBaseName(f2.getName());
            if (!pattern.matcher(name1).matches()) {
                return 1;
            }
            if (!pattern.matcher(name2).matches()) {
                return 1;
            }
            return Integer.parseInt(name1) - Integer.parseInt(name2);
        }).forEach(file -> {
            try {
                textList.add(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("读取到Txt文件数：" + textList.size());
        log.info("读取到Txt文件数：{}", textList.size());
        return textList;
    }

    public static void main(String[] args) throws IOException {
        // readFiles("qdb").forEach(System.out::println);

        String html = readFiles("qdb").get(3);

        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();
        Elements els = body.children();
        for (Element el : els) {
            if (el.hasClass("ql-align-center")) {
                HtmlUtil.css(el, "text-align: center");
                HtmlUtil.css(el, "font-weight: bold");
            }
            System.out.println(el);
            System.out.println(el.children().size());
            System.out.println(el.classNames());
            System.out.println();
        }
    }
}
