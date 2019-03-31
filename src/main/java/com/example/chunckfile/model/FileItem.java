package com.example.chunckfile.model;


import lombok.Data;
import org.springframework.core.io.FileSystemResource;

/**
 * @Author:sxq
 * @Date: 2019/3/30
 * @Description:
 */
@Data
public class FileItem {

    private String md5;

    private int chunckIndex;//当前分块文件下标

    private int chunkNum;//总分块数

    private long chunkSize;//当前分块文件大小

    private long fileLength;//原始文件长度

    private String fileName;//原始文件名

    private FileSystemResource file;

}


