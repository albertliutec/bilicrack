package com.poor.bilicrack;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 破解bilibili windows客户端下载视频破解
 *
 * @author alberliu
 * @email alberliu@163.com
 */
public class Crack {

    /**
     * 从根目录进行破解
     *
     * @param rootPath 根目录路径
     */
    public void crackFiles(String rootPath) {
        List<File> mp4SourceList = this.searchFiles(rootPath);
        List<File> mp4TargetList = mp4SourceList.stream().map(item -> {
            String[] split = item.getName().split("\\.");
            split[0] += "0";
            String newPath = item.getParent() + "\\" + split[0] + "." + split[1];
            return new File(newPath);
        }).collect(Collectors.toList());

        for (int i = 0; i < mp4SourceList.size(); i++) {
            String result = this.crackFile(mp4SourceList.get(i), mp4TargetList.get(i));
            System.out.println(result);
        }

    }

    /**
     * 递归查找.mp4文件
     *
     * @param rootPath 根路径
     * @return mp4文件对象
     */
    private List<File> searchFiles(String rootPath) {
        File rootFile = new File(rootPath);
        List<File> rootFileList = new LinkedList<>();
        // 如果root是个file直接返回只有root的list
        if (rootFile.isFile()) {
            rootFileList.add(rootFile);
            return rootFileList;
        } else if (rootFile.isDirectory()) {
            File[] sonFiles = rootFile.listFiles();
            // 如果root是个dir但是空的，直接返回空数组
            if (sonFiles == null) {
                return rootFileList;
            }
            // 如果root是个dir且不空，遍历到空为止
//            List<File> sonFilesList = Arrays.asList(sonFiles);
            List<File> sonFilesList = Arrays.stream(sonFiles).collect(Collectors.toList());
            List<File> videoList = new LinkedList<>();
            File[] newFiles = null;
            while (sonFilesList.size() != 0) {
                File tmp = sonFilesList.get(0);
                // 看deque出的是文件还是dir
                // 文件收了
                if (tmp.isFile()) {
                    if (tmp.getName().endsWith(".mp4")) {
                        videoList.add(tmp);
                    }
                    // 目录再放回queue里继续遍历
                } else if (tmp.isDirectory()) {
                    newFiles = tmp.listFiles();
                    if (newFiles != null) {
                        List<File> newFilesList = Arrays.asList(newFiles);
                        sonFilesList.addAll(newFilesList);
                    }
                }
                sonFilesList.remove(0);
            }
            return videoList;
        }
        return rootFileList;
    }

    /**
     * 文件拷贝破解
     *
     * @param source 源文件路径
     * @param target 目的文件路径
     * @return 成功或失败
     */
    private String crackFile(File source, File target) {
        FileInputStream input = null;
        FileOutputStream output = null;

        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(target);
            // 检查是否是第一次循环
            int flag = 1;
            int hasread = 0;
            byte[] cache = new byte[1024 * 1024 * 1024];
            while ((hasread = input.read(cache)) > 0) {
                if (flag == 1) {
                    output.write(cache, 3, hasread);
                } else {
                    output.write(cache, 0, hasread);
                }
                flag++;
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return source + " ： failed";
        } finally {
            //关闭管道
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 删除源文件并重命名
        if (source.delete() && target.renameTo(source)) {
            return source + " ： success";
        }else {
            return source + " ： failed";
        }
    }

    /**
     * 读取配置文件中的根路径
     *
     * @return path
     */
    public String readPath() {
        InputStream input = null;
        InputStreamReader inputReader = null;
        BufferedReader reader = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("path.txt");
            if (input != null) {
                inputReader = new InputStreamReader(input);
                reader = new BufferedReader(inputReader);
                return reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
