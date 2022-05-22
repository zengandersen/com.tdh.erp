//package com.tdh.swagger;
//
//
//import io.github.swagger2markup.GroupBy;
//import io.github.swagger2markup.Language;
//import io.github.swagger2markup.Swagger2MarkupConfig;
//import io.github.swagger2markup.Swagger2MarkupConverter;
//import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
//import io.github.swagger2markup.markup.builder.MarkupLanguage;
//import org.asciidoctor.*;
//
//import java.io.File;
//import java.net.URL;
//import java.nio.file.Paths;
//import java.util.HashMap;
//
///**
// * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
// * @since 2018/10/11
// */
//public class ExportSwagger {
//    /**
//     * swagger api转成　adoc  文件
//     *
//     * @param apiUrl       swagger 文档的访问路径
//     *                     例如:/v2/api-docs?group=registercompany
//     * @param adocFilePath 生成的adoc文件保存路径
//     *                     例如: asciidoc\generated
//     *                     返回值: 会在该路径下看到 asciidoc\generated.adoc文件
//     * @return boolean 创建结果
//     * @throws Exception 创建失败抛出异常
//     */
//    public static boolean swaggerApiToAdoc(String apiUrl, String adocFilePath) throws Exception {
//        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
//                .withMarkupLanguage(MarkupLanguage.ASCIIDOC) // 此处修改为 MarkupLanguage.MARKDOWN  即可生成md文档
//                .withPathsGroupedBy(GroupBy.TAGS)
//                .withOutputLanguage(Language.ZH)
//                .withGeneratedExamples()
//                .withoutInlineSchema()
//                .build();
//        Swagger2MarkupConverter.Builder from =
//                Swagger2MarkupConverter.from(new URL(apiUrl));
//        Swagger2MarkupConverter.Builder builder = from.withConfig(config);
//        Swagger2MarkupConverter build = builder.build();
//        if (adocFilePath.endsWith(".adoc")) {
//            adocFilePath = adocFilePath.substring(0, adocFilePath.length() - 5);
//        }
//        build.toFile(Paths.get(adocFilePath));
//        return true;
//    }
//
//    public static boolean adocToHtml(String adocFilePath, String baseDir) {
//
//        System.setProperty("jruby.compile.mode", "OFF");
//        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
//        Options options = new Options();
//        // 建立新的目录
//        options.setMkDirs(true);
//        // 保存到文件
//        options.setToFile(true);
//        // 设置安全等级
//        options.setSafe(SafeMode.UNSAFE);
//        // 生成html5
//        options.setBackend("html5");
//        // 属性配置
//        HashMap<String, Object> attributes = new HashMap<>(2);
//        // 左侧
//        attributes.put(Attributes.TOC, Placement.LEFT.getPosition());
//        // 设置目录 编号 编号等级
//        attributes.put("toclevels", 4);
//        attributes.put(Attributes.SECTION_NUMBERS, true);
//        attributes.put(Attributes.SECT_NUM_LEVELS, 4);
//        attributes.put(Attributes.TITLE, "apiWendang");
//
//        options.setAttributes(attributes);
//        options.setBaseDir(baseDir);
//        asciidoctor.convertFile(new File(adocFilePath), options);
//        return true;
//    }
//
//    public static void main(String[] args) throws Exception {
////        String apiUrl = "http://127.0.0.1:8086/v2/api-docs?group=user";
//        String adocFilePath = "D:\\examLog\\logFile";
//        String baseDir = "D:\\examLog\\logFile";
////        boolean createAdocFlag = swaggerApiToAdoc(apiUrl, adocFilePath);
//        boolean adocToHtmlFlag = adocToHtml(adocFilePath, baseDir);
//    }
//}
