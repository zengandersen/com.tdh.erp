package com.tdh.common;

import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Clob;

import static cn.hutool.core.img.ImgUtil.toBufferedImage;

public class ImgUtils {


    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    public static String imgToio (String imgUrl) throws Exception{
        File f = new File(imgUrl);
        BufferedImage bi;
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
            byte[] bytes = baos.toByteArray();
            String str = encoder.encodeBuffer(bytes).trim();
            return str;
    }

    /**
     * 将二进制转换为图片
     *
     * @param base64String
     */
    private static void base64StringToImage(String base64String) {
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File("f://files//timg.png");// 可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String clobToString(Clob clob) throws Exception {
        String re = "";
        Reader is = null;
        BufferedReader br = null;
        try {
            // 得到流
            is = clob.getCharacterStream();
            br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
            re = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return re;
    }
    public static void inputstreamtofile(InputStream ins,File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }


    /**
     * 将图片转换为base64格式
     *
     * @param imageUrl：图片路径
     * @param sizeLimit：原图大小上限，当图片原图大小超过该值时先将图片大小 设置为该值以下再转换成base64格式,单位kb
     * @return
     */
    public static String convertImageToBase64(String imageUrl, Integer sizeLimit) throws IOException {
        // 默认上限为500k
        File f = new File(imageUrl);
        if (sizeLimit == null) {
            sizeLimit = 500;
        }
        sizeLimit = sizeLimit * 1024;
        String base64Image;
        DataInputStream dataInputStream = null;
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            // 从远程读取图片

            InputStream in = new FileInputStream(f);
            dataInputStream = new DataInputStream(in);
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[6000];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            byte[] context = outputStream.toByteArray();

            // 将图片数据还原为图片
            inputStream = new ByteArrayInputStream(context);
            //图片可能变色
            BufferedImage image = ImageIO.read(inputStream);
            //使用该方法解决 图片压缩变色问题  ImageIO.read（）会存在图片变色问题
//            Image src = Toolkit.getDefaultToolkit().getImage(imageUrl);
//            BufferedImage image = toBufferedImage(src);
            int imageSize = context.length;

            int type = image.getType();
            int height = image.getHeight();
            int width = image.getWidth();

            BufferedImage tempImage;
            // 判断文件大小是否大于size,循环压缩，直到大小小于给定的值

            System.out.println(sizeLimit);
            while (imageSize > sizeLimit) {
                System.err.println(imageSize);
                // 将图片长宽压缩到原来的90%
                height = new Double(height * 0.9).intValue();
                width = new Double(width * 0.9).intValue();
                tempImage = new BufferedImage(width, height, type);
                // 绘制缩小后的图
                tempImage.getGraphics().drawImage(image, 0, 0, width, height, null);
                // 重新计算图片大小
                outputStream.reset();
                ImageIO.write(tempImage, "jpg", outputStream);
                imageSize = outputStream.toByteArray().length;

            }

            // 将图片转化为base64并返回
            byte[] data = outputStream.toByteArray();
            // 此处一定要使用org.apache.tomcat.util.codec.binary.Base64，防止再linux上出现换行等特殊符号
            base64Image = Base64.encodeBase64String(data);
        } catch (Exception e) {
            // 抛出异常
            throw e;
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        f.delete();
        String result =  "data:image/png;base64,"+base64Image;
        return result;
    }
}
