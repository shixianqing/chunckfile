package com.example.chunckfile.util;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
public class Md5Util {

    public static String getMD5(File file) {

        FileInputStream fileInputStream = null;
        MessageDigest MD5;

        try {

            MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;

            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //将字节数组转换为字符数组
        return new String(Hex.encodeHex(MD5.digest()));
    }
}


