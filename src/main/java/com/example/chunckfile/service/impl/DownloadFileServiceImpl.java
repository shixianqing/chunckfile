package com.example.chunckfile.service.impl;

import com.example.chunckfile.http.INetService;
import com.example.chunckfile.http.response.ResponseVo;
import com.example.chunckfile.service.DownloadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
@Service
public class DownloadFileServiceImpl implements DownloadFileService {

    @Autowired
    @Qualifier("tpaFileDownloadNetServiceImpl")
    private INetService<Object> downloadService;

    @Override
    public boolean download() {

        ResponseVo responseVo = downloadService.execute("", null);

        return false;
    }
}


