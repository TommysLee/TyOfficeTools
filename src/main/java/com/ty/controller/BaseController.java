package com.ty.controller;

import com.ty.constant.MIME;
import com.ty.util.WebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;

/**
 * Base Controller
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
@Slf4j
public class BaseController {

    /** 默认缓冲区大小 **/
    protected static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * 文件下载（基于Java NIO）
     */
    @SuppressWarnings("resource")
    protected void download(HttpServletResponse response, String fileName, String mime, File file) throws Exception {
        if (file.exists() && file.isFile()) {
            if (StringUtils.isNotBlank(fileName)) {
                final String agent = WebUtil.getUserAgent();
                if (StringUtils.isNotBlank(agent) && agent.toLowerCase().contains("firefox")) {
                    fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
                } else {
                    fileName = WebUtil.encodeU8(fileName);
                }
            }

            // 下载参数设置
            response.reset();
            response.setContentType(StringUtils.isNotBlank(mime) ? mime : MIME.STREAM); // MIME
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName); // 下载文件名称
            response.addHeader("Content-Length", "" + file.length()); // 下载文件大小

            try (
                    OutputStream outStream = response.getOutputStream(); // 输出流
                    WritableByteChannel writeChannel = Channels.newChannel(outStream); // 文件通道写
                    FileChannel readChannel = new FileInputStream(file).getChannel(); // 文件通道读
            ) {
                ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
                while (-1 != readChannel.read(buffer)) {
                    buffer.flip();
                    try {
                        writeChannel.write(buffer);
                    } catch (Exception ie) {
                        log.warn("用户在下载文件时，可能主动终止了下载！！！");
                        break;
                    }
                    buffer.clear();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            WebUtil.writeText(response, "文件 \"" + fileName + "\" 丢失或不是文件！");
        }
    }

    /**
     * 文件下载（基于Java NIO）
     */
    protected void download(HttpServletResponse response, String fileName, String mime, String path) throws Exception {
        File file = new File(path);
        this.download(response, fileName, mime, file);
    }
}
