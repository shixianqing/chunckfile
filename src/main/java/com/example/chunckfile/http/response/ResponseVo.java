package com.example.chunckfile.http.response;

import lombok.Data;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
@Data
public class ResponseVo {

    public static final String SERVER_SUCCESS = "SUCCESS";
    public static final String SERVER_FAIL = "FAIL";

    private String serverRespStatus;//服务器响应状态

    private String serverRespDesc;//服务器响应描述

    private Object result;//响应报文
}


