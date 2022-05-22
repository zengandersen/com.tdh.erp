package com.tdh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesUtils {


    public static String readProp(String confName, String filedName) {
        ResourceBundle bundle = ResourceBundle.getBundle(confName);
        String result = bundle.getString(filedName).trim(); // 过滤读取结果的空格
        return result;
    }


    public static String getProperties(String info) throws IOException {
        String path = PropertiesUtils.class.getResource("/").getPath();
        String websiteURL = (path.replace("/build/classes", "").replace("%20"," ") + "config.properties").replaceFirst("/", "");
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        String result = "";
        try {
            fileInputStream = new FileInputStream(websiteURL);
            properties.load(fileInputStream);
            result = properties.getProperty(info);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }



}
