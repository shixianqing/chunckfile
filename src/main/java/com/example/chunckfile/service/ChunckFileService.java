package com.example.chunckfile.service;

import com.example.chunckfile.model.FileItem;

import java.util.List;

/**
 * @Author:sxq
 * @Date: 2019/3/30
 * @Description:
 */
public interface ChunckFileService {


    List<FileItem> chunkFile(String filePath, String temFilePath, String fileName);
}
