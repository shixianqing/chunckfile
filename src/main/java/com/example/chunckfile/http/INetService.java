package com.example.chunckfile.http;

import com.example.chunckfile.http.response.ResponseVo;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
public interface INetService<T> {


    ResponseVo execute(String url, T params);
}
