package com.example.chunckfile.http.impl;

import com.example.chunckfile.http.INetService;
import com.example.chunckfile.http.response.ResponseVo;
import com.example.chunckfile.model.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
@Service
public class TpaChunkFIleNetServiceImpl implements INetService<FileItem> {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TpaChunkFIleNetServiceImpl.class);

    /**
     * 上传文件通讯
     * @param url 请求URL
     * @param params 请求参数
     * @return ResponseVo
     */
    @Override
    public ResponseVo execute(String url, FileItem params) {
        HttpHeaders httpHeaders = new HttpHeaders();
        setHttpHeaders(httpHeaders);

        MultiValueMap<String,Object> form = new LinkedMultiValueMap<>();

        convertParamsToMap(params,form);

        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(form,httpHeaders);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);

        HttpStatus status = responseEntity.getStatusCode();

        logger.info("response status code is {}，response status desc is {}",status.value(),status.getReasonPhrase());

        ResponseVo responseVo = new ResponseVo();

        responseVo.setServerRespStatus(status.is2xxSuccessful()?
                ResponseVo.SERVER_SUCCESS:ResponseVo.SERVER_FAIL);

        responseVo.setServerRespDesc(status.getReasonPhrase());
        responseVo.setResult(responseEntity.getBody());

        return responseVo;
    }

    private void convertParamsToMap(FileItem params, MultiValueMap<String, Object> form) {

        Class<? extends FileItem> paramsClass = params.getClass();
        Field[] fields = paramsClass.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            try {
                Object fieldVal = field.get(params);
                form.add(field.getName(),fieldVal);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void setHttpHeaders(HttpHeaders httpHeaders) {

        httpHeaders.add("serviceNo", "tpa标示");
        httpHeaders.add("serviceKey", "标识key");
        httpHeaders.add("fileType ", "4");//结案文件

        MediaType mediaType = MediaType.MULTIPART_FORM_DATA;//文件上传表单
        httpHeaders.setContentType(mediaType);
    }
}


