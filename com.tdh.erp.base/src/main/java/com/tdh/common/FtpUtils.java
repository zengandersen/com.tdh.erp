package com.tdh.common;


import com.tdh.pojo.OaFtp;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * FTP工具类
 * 文件上传
 * 文件下载
 */
public class FtpUtils {


    /**
     * 设置缓冲区大小4M
     **/
    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    /**
     * 本地字符编码
     **/
    private static String LOCAL_CHARSET = "GBK";

    /**
     * UTF-8字符编码
     **/
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * OPTS UTF8字符串常量
     **/
    private static final String OPTS_UTF8 = "OPTS UTF8";

    /**
     * FTP协议里面，规定文件名编码为iso-8859-1
     **/
    private static final String SERVER_CHARSET = "ISO-8859-1";

    private static FTPClient ftpClient = null;

    /**
     * 连接FTP服务器
     */
    private static void login(OaFtp oaFtp) {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(oaFtp.getIp(), Integer.valueOf(oaFtp.getPort()));
            ftpClient.login(oaFtp.getName(), oaFtp.getPwd());
            ftpClient.setBufferSize(BUFFER_SIZE);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                closeConnect();
            }
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭FTP连接
     */
    private static void closeConnect() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {

            }
        }
    }

    /**
     * FTP服务器路径编码转换
     *
     * @param ftpPath FTP服务器路径
     * @return String
     */
    private static String changeEncoding(String ftpPath) {
        String directory = null;
        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                LOCAL_CHARSET = CHARSET_UTF8;
            }
            directory = new String(ftpPath.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
        } catch (Exception e) {

        }
        return directory;
    }

    /**
     * 改变工作目录
     * 如果没有，则创建工作目录
     * @param path
     */
    private static void changeAndMakeWorkingDir(String path) {
        try {
            ftpClient.changeWorkingDirectory("/");
            path = path.replaceAll("\\\\","/");
            String[] path_array = path.split("/");
            for (String s : path_array) {
                boolean b = ftpClient.changeWorkingDirectory(s);
                if (!b) {
                    ftpClient.makeDirectory(s);
                    ftpClient.changeWorkingDirectory(s);
                }
            }
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * 上传
     * @param oaFtp
     * @param filename
     * @param dirPath
     * @param in
     * @return
     */
    public static boolean upload (OaFtp oaFtp, String filename, String dirPath, InputStream in) {
        login(oaFtp);
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean isSuccess = false;

        if (ftpClient != null) {
            try {
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                    LOCAL_CHARSET = CHARSET_UTF8;
                }
                ftpClient.setControlEncoding(LOCAL_CHARSET);
                String path = changeEncoding(dirPath);

                changeAndMakeWorkingDir(path);
                isSuccess = ftpClient.storeFile(new String(filename.getBytes(), SERVER_CHARSET), in);
            } catch (Exception e) {

            } finally {
                closeConnect();
            }
        }
        return isSuccess;
    }

    /**
     * 下载
     * @param oaFtp
     * @param filename
     * @param dirPath
     * @param out
     * @return
     */
    public static void download (OaFtp oaFtp, String filename, String dirPath, FileOutputStream out) {
        // 登录
        login(oaFtp);
        if (ftpClient != null) {
            try {
                String path = changeEncoding(dirPath);
                changeAndMakeWorkingDir(path);
                String[] fileNames = ftpClient.listNames();
                if (fileNames == null || fileNames.length == 0) {
                    return;
                }
                for (String fileName : fileNames) {
                    String ftpName = new String(fileName.getBytes(SERVER_CHARSET), LOCAL_CHARSET);
                    if (StringUtils.equals(ftpName,filename)) {
                        InputStream in = ftpClient.retrieveFileStream(fileName);
                        IOUtils.copy(in,out);
                    }
                }
            } catch (IOException e) {

            } finally {
                closeConnect();
            }
        }
    }
}