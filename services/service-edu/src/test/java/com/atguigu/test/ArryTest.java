package com.atguigu.test;

import org.junit.Test;

import java.io.File;

/**
 * @author li
 * @create 2021-04-29 22:55
 */
public class ArryTest {

    @Test
    public void Test1(){
        String path1="D:\\IOTest/sparseArray.txt";
        String path2="D:\\IOTest.sparseArray.txt";

        File file1 = new File(path1);
        File file2 = new File(path2);

        String r1 = file1.getPath();
        String r2 = file2.getPath();

        System.out.println(file1.exists());
        System.out.println(file2.exists());


        System.out.println(r1);
        System.out.println(r2);
    }
}
