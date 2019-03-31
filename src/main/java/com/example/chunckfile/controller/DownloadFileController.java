package com.example.chunckfile.controller;

import com.example.chunckfile.http.INetService;
import com.example.chunckfile.http.response.ResponseVo;
import com.example.chunckfile.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
@RestController
public class DownloadFileController {

    @Autowired
    @Qualifier("tpaFileDownloadNetServiceImpl")
    private INetService<Object> downloadService;

    @GetMapping("/download")
    public void download() throws IOException {

        ResponseVo responseVo = downloadService.execute("http://localhost:8081/download", null);

        if (responseVo.getServerRespStatus().equals(ResponseVo.SERVER_SUCCESS)){
            Resource resource = (Resource) responseVo.getResult();
            boolean flag = FileUtils.createFile(resource.getInputStream(),"g:/download","download.zip");
            System.out.println(flag);
        }
    }


}



