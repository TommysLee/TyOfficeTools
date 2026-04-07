package com.ty.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.ty.constant.MIME;
import com.ty.entity.AjaxResult;
import com.ty.entity.Data;
import com.ty.entity.Que;
import com.ty.service.DataServcie;
import com.ty.util.DateUtil;
import com.ty.util.FileUtil;
import com.ty.util.HtmlUtil;
import com.ty.util.office.WordUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 题集示例数据与生成Controller
 *
 * @Author Tommy
 * @Date 2025/12/03
 */
@Controller
@RequestMapping("/que")
public class QueController extends BaseController {

    @Autowired
    private DataServcie dataServcie;

    @Value("${ty.saveDir}")
    private String saveDir;

    private Cache<String, File> historyCache = CacheBuilder.newBuilder().maximumSize(100).build();

    @RequestMapping("/data")
    @ResponseBody
    public AjaxResult data(Data data) throws Exception {
        return AjaxResult.success(dataServcie.queDataList());
    }

    @RequestMapping("/export_word")
    @ResponseBody
    public AjaxResult exportWord(@RequestBody ArrayList<Que> list, @RequestParam String name, @RequestParam Integer cols) throws Exception {
        File templateFile = FileUtil.tempalte(null != cols && 2 == cols? "template-que-col-2.docx" : "template-que-col-1.docx");
        String savePath = saveDir + UUID.randomUUID() + ".docx";

        int index = 1;
        for (Que que : list) {
            que.setIndex(index++);
            que.setHtml(HtmlUtil.resolve(que.getHtml()));
        }

        long begin = System.currentTimeMillis();
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("name", name);
        dataMap.put("time", DateUtil.now());
        dataMap.put("queList", list);
        File wordFile = WordUtil.write(dataMap, templateFile, savePath);
        long end = System.currentTimeMillis();

        String uuid = UUID.randomUUID().toString();
        historyCache.put(uuid, wordFile);;
        return AjaxResult.success("耗时：" + (end - begin) + "ms.", uuid);
    }

    @RequestMapping("/download")
    public void download(HttpServletResponse response, String uuid) throws Exception {
        File file = historyCache.getIfPresent(uuid);
        this.download(response, "精品题集-" + uuid + ".docx", MIME.WORD, file);
    }
}
