package com.example.chunckfile.http.impl;

import com.example.chunckfile.http.INetService;
import com.example.chunckfile.http.response.ResponseVo;
import com.example.chunckfile.model.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
@Service
public class TpaFileDownloadNetServiceImpl implements INetService<Object> {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TpaChunkFIleNetServiceImpl.class);


    @Override
    public ResponseVo execute(String url, Object params) {

        HttpHeaders httpHeaders = new HttpHeaders();
        setHttpHeaders(httpHeaders);


        HttpEntity<Object> httpEntity = new HttpEntity<>(null,httpHeaders);

        // 接收响应输入流，Resource
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Resource.class);

        HttpStatus status = responseEntity.getStatusCode();

        logger.info("response status code is {}，response status desc is {}",status.value(),status.getReasonPhrase());

        ResponseVo responseVo = new ResponseVo();

        responseVo.setServerRespStatus(status.is2xxSuccessful()?
                ResponseVo.SERVER_SUCCESS:ResponseVo.SERVER_FAIL);

        responseVo.setServerRespDesc(status.getReasonPhrase());
        responseVo.setResult(responseEntity.getBody());

        return responseVo;
    }


    private void setHttpHeaders(HttpHeaders httpHeaders) {

        httpHeaders.add("serviceNo", "tpa标示");
        httpHeaders.add("serviceKey", "标识key");
        httpHeaders.add("fileType ", "6");//结案文件

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;//文件上传表单
        httpHeaders.setContentType(mediaType);
    }
}


