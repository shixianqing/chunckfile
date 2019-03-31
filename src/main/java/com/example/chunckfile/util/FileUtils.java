package com.example.chunckfile.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static void deleteTmpFile(File temp) {

        if (!temp.exists()){
            logger.error("{} is not exsit",temp.getName());
            return;
        }

        if (temp.isDirectory()){
            File[] files = temp.listFiles();
            for (File file : files){
                if (file.isDirectory()){
                    deleteTmpFile(file);
                } else {
                    file.delete();
                }
            }
        } else {
            temp.delete();
        }
    }




    public static boolean createFile(InputStream inputStream,String targetFilePath,String targetFileName){

        if (ObjectUtils.isEmpty(targetFilePath) || ObjectUtils.isEmpty(targetFileName)){
            logger.error("target file path or name is not empty!");
            throw new RuntimeException("targetFilePath or targetFileName is not empty!");
        }

        if (ObjectUtils.isEmpty(inputStream)){
            logger.error("inputStream is not empty");
            return false;
        }

        File parentFile = new File(targetFilePath);
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }

        File targetFile = new File(parentFile,targetFileName);

        FileOutputStream fos = null;

        int len;
        byte[] bytes = new byte[1024*1024];

        try{
            fos = new FileOutputStream(targetFile);
            while ((len = inputStream.read(bytes)) != -1){
                fos.write(bytes,0,len);
            }
        }catch (IOException e){
            logger.error("write file occur exception --- {}",e);
            return false;
        }finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}


