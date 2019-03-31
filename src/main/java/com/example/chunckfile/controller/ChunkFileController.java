package com.example.chunckfile.controller;

import com.example.chunckfile.http.INetService;
import com.example.chunckfile.http.response.ResponseVo;
import com.example.chunckfile.model.FileItem;
import com.example.chunckfile.service.ChunckFileService;
import com.example.chunckfile.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
@RestController
public class ChunkFileController {

    @Autowired
    private ChunckFileService chunckFileService;

    @Autowired
    @Qualifier("tpaChunkFIleNetServiceImpl")
    private INetService<FileItem> netService;


    @GetMapping("/test")
    public void test(){

        String tempFilePath = "g:/file";
        List<FileItem> fileItems = chunckFileService.chunkFile("g:/",tempFilePath,"test.zip");

        for (FileItem fileItem: fileItems){
            ResponseVo responseVo = netService.execute("http://localhost:8081/upload", fileItem);
            if (responseVo.getServerRespStatus().equals(ResponseVo.SERVER_SUCCESS)){
                System.out.println(responseVo.getResult());
            }
        }

        //删除临时文件
        FileUtils.deleteTmpFile(new File(tempFilePath));
    }
}


