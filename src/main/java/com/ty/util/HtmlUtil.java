package com.ty.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Html Util类
 *
 * @Author Tommy
 * @Date 2025/12/2
 */
public class HtmlUtil {

    /**
     * 解析特定元素，并格式化为POI可识别的格式
     *
     * @param html HTML 片段
     * @return String
     */
    public static String resolve(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        doc.outputSettings().prettyPrint(false);
        Element body = doc.body();

        Elements els = body.children();
        for (Element el : els) {
            recursiveResolve(el);
        }
        // System.out.println(body.html());
        // System.out.println();
        return body.html();
    }

    /**
     * 递归解析全部元素
     */
    static void recursiveResolve(Element el) {
        el.classNames().forEach(cssClass -> {
            switch (cssClass) {
                case "ql-formula":
                    el.tagName("latex");
                    break;
                case "ql-align-center":
                    css(el, "text-align: center");
                    break;
                case "ql-align-right":
                    css(el, "text-align: right");
                    break;
                case "ql-align-justify":
                    css(el, "text-align: justify");
                    break;
                case "ql-font-songti":
                    css(el, "font-family: '宋体', 'SimSun'");
                    break;
                case "ql-font-yahei":
                    css(el, "font-family: '微软雅黑', 'Microsoft YaHei'");
                    break;
                case "ql-font-kaiti":
                    css(el, "font-family: '楷体', '楷体_GB2312', 'SimKa'");
                    break;
                case "ql-font-heiti":
                    css(el, "font-family: '黑体', 'SimHei'");
                    break;
                case "ql-font-lishu":
                    css(el, "font-family: '隶书', 'SimLi'");
                    break;
            }
        });

        // 递归处理子元素
        Elements children = el.children();
        for (Element child : children) {
            recursiveResolve(child);
        }
    }

    /**
     * 解析LaTex，并格式化为POI可识别的格式
     *
     * 这个方法用于解析 华为云官方TinyEditor数据
     *
     * @param html HTML 片段
     * @return String
     */
    public static String resolveLatex(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();

        Elements els = body.select(".ql-formula");
        int size = els.size();
        System.out.println("识别到公式个数：" + size);
        for (int i = 0; i < size; i++) {
            Element el = els.get(i);
            String latex = el.attr("data-value");
            el.tagName("latex");
            el.html("$" + latex + "$");
        }
        // System.out.println(body.html());
        return body.html();
    }

    /**
     * 给元素添加 style 样式
     *
     * @param el    HTML元素
     * @param style 样式
     */
    public static void css(Element el, String style) {
        String key = "style";
        String fullStyle = StringUtils.defaultIfBlank(el.attr(key), StringUtils.EMPTY);
        if (StringUtils.isNotBlank(fullStyle) && !StringUtils.endsWith(fullStyle, ";")) {
            fullStyle += ";";
        }
        el.attr(key, fullStyle + style);
    }
}
