package com.example.chunckfile;

import java.io.File;

/**
 * @Author:sxq
 * @Date: 2019/3/31
 * @Description:
 */
public class Test {


    public static void main(String[] args) {

        File file = new File("G:\\test\\test.txt");

        System.out.println(file.getAbsolutePath());

        System.out.println(file.getPath());
    }
}


