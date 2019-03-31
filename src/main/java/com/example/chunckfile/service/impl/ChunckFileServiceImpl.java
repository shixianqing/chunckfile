package com.example.chunckfile.service.impl;

import com.example.chunckfile.model.FileItem;
import com.example.chunckfile.service.ChunckFileService;
import com.example.chunckfile.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:sxq
 * @Date: 2019/3/30
 * @Description:
 */
@Service
public class ChunckFileServiceImpl implements ChunckFileService {

    private static final Logger logger = LoggerFactory.getLogger(ChunckFileServiceImpl.class);


    /**
     *
     * @param filePath 源文件路径
     * @param temFilePath 临时文件路径
     * @param fileName 源文件名称
     */
    @Override
    public List<FileItem> chunkFile(String filePath,String temFilePath,String fileName) {



        File srcFile = new File(filePath,fileName);

        if (!srcFile.exists()){
            srcFile.mkdirs();
        }

        //获取源文件的md5码
        String md5 = Md5Util.getMD5(srcFile);

        logger.info("{}的md5值为：{}",fileName,md5);

        //计算分割后每个文件的大小
        long srcFileSize = srcFile.length();
        long deskFileSize = 10 * 1024 * 1024;

        //计算要分割出多少份文件
        int num = (int) (srcFileSize / deskFileSize);
        num = srcFileSize % deskFileSize == 0? num:num+1;

        //获取源文件名称
        String srcFileName = fileName.substring(0,fileName.indexOf("."));

        byte[] bytes = new byte[10*1024*1024];//分块文件大小


        //封装分块文件
        return chunkFilePackage(temFilePath,num,deskFileSize,srcFileName,
                bytes,srcFile,srcFileSize,md5);


    }

    private List<FileItem> chunkFilePackage(String temFilePath, int num, long deskFileSize,
                                  String srcFileName, byte[] bytes,File srcFile,
                                  long srcFileSize,String md5) {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos;
        int len;

        List<FileItem> fileItems = new ArrayList<>();

        try {
            fis = new FileInputStream(srcFile);
            bis = new BufferedInputStream(fis);
            for (int i=1; i<=num; i++){

                String tempFileName = srcFileName + "-" + i + ".tmp";
                File temp = new File(temFilePath);
                if (!temp.exists()){
                    temp.mkdirs();
                }
                fos = new FileOutputStream(temp.getAbsolutePath() + File.separator + tempFileName);
                bos = new BufferedOutputStream(fos);
                int count = 0;

                //将分块文件写进临时文件里
                while ((len = bis.read(bytes)) != -1){
                    bos.write(bytes,0,len);
                    count += len;
                    if (count >= deskFileSize) break;
                }

                bos.flush();
                bos.close();
                fos.close();

                FileItem fileItem = new FileItem();
                fileItem.setChunckIndex(i);
                fileItem.setChunkNum(num);
                fileItem.setChunkSize(len);
                fileItem.setFile(new FileSystemResource(temp.getAbsolutePath() + File.separator + tempFileName));
                fileItem.setFileLength(srcFileSize);
                fileItem.setMd5(md5);
                fileItem.setFileName(srcFileName);
                fileItems.add(fileItem);

            }
        } catch (Exception e){
            logger.error("file chunk fail {}",e);
            throw new RuntimeException(e);
        }finally {


            try {
                if (bis != null){
                    fis.close();
                }
                if (fis != null){
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        logger.info("file chunk success!, chunkFile's number is {}",fileItems.size());

        return fileItems;
    }


}


