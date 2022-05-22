package com.tdh.common;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    /**
     * 查询文件信息
     *
     * @param path
     * @return
     */
    public static String[] getFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] arr = new String[files.length];
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            for (int i = 0; i < files.length; i++) {
                arr[i] = files[i].getPath();
            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
        return arr;
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public static File createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件
     *
     * @param path
     * @return
     */
    public static File createFiletxt(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据文件对象写入信息
     *
     * @param file
     * @param date
     * @param sysTrackCode
     * @param event
     * @param msg
     * @throws IOException
     */
    public static void info(File file, String date, String sysTrackCode, String event, String msg) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("-[" + date + "]-" + sysTrackCode + "-[" + event + "]-" + msg + "\r\n");
        bw.close();
        fw.close();
    }

    public static void writeTableInfo(){
    }

    public static void generateFile(String path, String format) throws Exception {

        File file = new File(path);
        // 压缩文件的路径不存在
        if (!file.exists()) {
            throw new Exception("路径 " + path + " 不存在文件，无法进行压缩...");
        }
        // 用于存放压缩文件的文件夹
        String generateFile = PropertiesUtils.getProperties("log.compression.path");
        File compress = new File(generateFile);
        // 如果文件夹不存在，进行创建
        if( !compress.exists() ){
            compress.mkdirs();
        }
        String fileName = "";
        File[] files = file.listFiles();
        if(files.length > 0){
            for(int i=0;i<files.length;i++){
                if(!String.valueOf(files[i]).contains(PropertiesUtils.getProperties("Compression.format"))){
                    if(i<= Integer.parseInt(PropertiesUtils.getProperties("Compression.quantity"))){
                        if(i==0){
                            String [] arr = String.valueOf(files[i]).split("\\\\");
                            fileName = String.valueOf(arr[3]);
                        }
                        if(i==Integer.parseInt(PropertiesUtils.getProperties("Compression.quantity"))){
                            String [] arr = String.valueOf(files[i]).split("\\\\");
                            fileName = fileName+"~"+String.valueOf(arr[3]);
                        }
                    }
                }
            }
        }else{
            throw new Exception("路径 " + path + " 不存在文件，无法进行压缩...");
        }

        // 目的压缩文件
        String generateFileName = compress.getAbsolutePath() + File.separator + fileName + "." + format;

        // 输入流 表示从一个源读取数据
        // 输出流 表示向一个目标写入数据

        // 输出流
        FileOutputStream outputStream = new FileOutputStream(generateFileName);

        // 压缩输出流
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));

        generateFile(zipOutputStream,file,"");

        System.out.println("源文件位置：" + file.getAbsolutePath() + "，目的压缩文件生成位置：" + generateFileName);
        // 关闭 输出流
        zipOutputStream.close();
    }


    private static void generateFile(ZipOutputStream out, File file, String dir) throws Exception {
        // 当前的是文件夹，则进行一步处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] files = file.listFiles();
            //将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(dir + "/"));
            dir = dir.length() == 0 ? "" : dir + "/";
            //循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                if(!String.valueOf(files[i]).contains(PropertiesUtils.getProperties("Compression.format"))){
                    if(i<= Integer.parseInt(PropertiesUtils.getProperties("Compression.quantity"))){
                        //打包文件
                        generateFile(out, files[i], dir + files[i].getName());
                        files[i].delete();
                    }
                }
            }
        } else { // 当前是文件
            // 输入流
            FileInputStream inputStream = new FileInputStream(file);
            // 标记要打包的条目
            out.putNextEntry(new ZipEntry(dir));
            // 进行写操作
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
            // 关闭输入流
            inputStream.close();
        }

    }
}
