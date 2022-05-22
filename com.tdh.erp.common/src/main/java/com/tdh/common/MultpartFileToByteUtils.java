package com.tdh.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MultpartFileToByteUtils {

    //二进制文件转换MultipartFile
    private MultipartFile getMultipartFile(byte[] bytes) {
        System.out.println("二进制转换MultipartFile开始");
        MockMultipartFile mockMultipartFile = null;
        //java7新特性  不用手动关闭流
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            mockMultipartFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("二进制文件转换图片异常");
        }
        return mockMultipartFile;
    }

    //MultipartFile转换为二进制
    public byte[] getByte(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        return bytes;

    }

    //file转MultipartFile
    public MultipartFile getMultipartFile() throws IOException {
        String filePath = "D:\\背景\\微信图片_20191023172821.jpg";
        /*String filePath = "D:\\背景\\微信图片3.jpg";*/
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        // MockMultipartFile(String name, @Nullable String originalFilename, @Nullable String contentType,
        // InputStream contentStream)
        // 其中originalFilename,String contentType 旧名字，类型  可为空
        // ContentType.APPLICATION_OCTET_STREAM.toString() 需要使用HttpClient的包
        MultipartFile multipartFile = new MockMultipartFile("copy" + file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        return multipartFile;
    }

}


