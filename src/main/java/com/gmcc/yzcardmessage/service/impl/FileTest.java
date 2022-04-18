package com.gmcc.yzcardmessage.service.impl;


import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTest {
    public static void main(String args[]){
        method3("d://text.txt", "\r\n"+"ABCDEFGHIJKLMN");
        method1("d://text.txt","\r\n"+"ABCDEFGHIJKLMN");
        method3("d://text.txt","\r\n"+"ABCDEFGHIJKLMN12345789");
        System.out.println("开始追加文件");
        method2("d://text.txt","\r\n"+"ABCD123890");
        method1("d://aa.test","\r\n"+"ABCDFGJKLPMNOC");
        method2("d://text.txt","\r\n"+"添加数据类型参数");
        method3("d://text.txt","\r\n"+"FDHJKLMNFILE");
        method3("d://text.txt","\r\n"+"GHFIKOINLMNPQR");
        method1("d://text.txt","\r\n"+"copy contextText");
        method2("d://text.txt","\r\n"+"");
    }

    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void method6(String file,String conent){
        BufferedWriter  out = null;
        try{
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            out.write(conent);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (out!=null){
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public static  void method2(String file, String content) {
        BufferedWriter  out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            out.write(content);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (out != null){
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content 追加的内容
     */
    public static void method3(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按读写方式
            randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(randomFile != null){
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
