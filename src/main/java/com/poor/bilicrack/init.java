package com.poor.bilicrack;

/**
 * 破解bilibili windows客户端下载视频破解
 *
 * @author alberliu
 * @email alberliu@163.com
 */
public class init {
    public static void main(String[] args) {
        Crack crack = new Crack();
        String sourcePath = crack.readPath();
        crack.crackFiles(sourcePath);
        System.out.println("破解完毕");
    }

//    private static void testCrackFile(com.poor.bilicrack.Crack crack) {
//        String sourcePath = "";
//        String targetPath = "";
//        String result = crack.crackFile(new File(sourcePath), new File(targetPath));
//        System.out.println(result);
//    }

//    private static void testSearchFile(com.poor.bilicrack.Crack crack) {
//        String rootPath = "";
//        List<File> fileList = crack.searchFiles(rootPath);
//        System.out.println(fileList);
//    }
}
