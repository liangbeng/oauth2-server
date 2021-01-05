package org.wzp.oauth2.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.wzp.oauth2.config.CustomConfig;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/16 11:48
 */
@Slf4j
public class FileUtil {


    /**
     * 判断文件是否存在，不存在则创建
     *
     * @param filePath 文件路径
     */
    private static void fileExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    /**
     * 遍历文件夹
     *
     * @param file
     */
    public static void traverseFile(File file) {
        File[] dirs = file.listFiles();
        for (File dir : dirs) {
            if (dir.isDirectory()) {
                traverseFile(dir);
            } else {
                //输出文件名称
                String filename = dir.getName();
                //输出文件全路径
                String filePath = dir.getPath();
                //输出文件的父文件名
                String parentDirectory = dir.getParentFile().getName();
                //输出文件父路径
                String parentDirectoryPath = dir.getParent();
            }
        }
    }


    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean delFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 删除目录（文件夹）以及目录下的文件(包括目录自身)
     *
     * @param file 文件
     * @return
     */
    public static boolean deleteFolder(File file) {
        boolean flag = true;
        if (file.exists()) {
            File[] filePaths = file.listFiles();
            for (File f : filePaths) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                }
                f.delete();
            }
            //删除当前目录
            if (file.isDirectory()) {
                file.delete();
            }
        } else {
            flag = false;
        }
        return flag;
    }


    /**
     * 获取文件名前缀
     *
     * @param fileName 123.zip
     * @return 123 不包括分隔符
     */
    public static String getFilePrefix(String fileName) {
        return ObjUtil.strPrefix(fileName, ".", 0);
    }


    /**
     * 获取文件名后缀
     *
     * @param fileName 123.zip
     * @return .zip 包括分隔符
     */
    public static String getFileSuffix(String fileName) {
        return ObjUtil.strSuffix(fileName, ".", 0);
    }


    /**
     * 复制文件
     *
     * @param srcFolder  待复制文件
     * @param destFolder 复制后的文件
     */
    public static void move(File srcFolder, File destFolder) {
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        File[] fileArray = srcFolder.listFiles();
        for (File file : fileArray) {
            if (file.isDirectory()) {
                String folderName = file.getName();
                File newDestFolder = new File(destFolder, folderName);
                move(file, newDestFolder);
            } else {
                String fileName = file.getName();
                File destFile = new File(destFolder, fileName);
                copy(file, destFile);
            }
        }
    }

    public static void copy(File file, File destFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            byte[] bys = new byte[1024];
            int len = 0;
            while ((len = bis.read(bys)) != -1) {
                bos.write(bys, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取文件的md5值
     *
     * @param path 文件路径
     * @return md5值
     */
    public static String fileMd5(String path) {
        String md5 = null;
        try {
            FileInputStream inputStream = new FileInputStream(ResourceUtils.getFile(path));
            md5 = DigestUtils.md5DigestAsHex(inputStream);
        } catch (Exception e) {
            log.error("get {} md5 error, {}", path, e.getMessage());
        }
        return md5;
    }


    /**
     * 文件重命名
     *
     * @param filePrefix 重命名前缀
     * @param filePath   文件路径
     */
    public static void renameFile(String filePrefix, String filePath) {
        File file = new File(filePath);
        File[] fileList = file.listFiles();
        int index = 1;
        for (File oldFile : fileList) {
            if (oldFile.isDirectory()) {
                renameFile(filePrefix, oldFile.getPath());
            } else {
                index = rename(oldFile, filePrefix, index);
            }
        }
    }

    private static int rename(File oldFile, String filePrefix, int index) {
        String newName = oldFile.getParent() + File.separator + filePrefix + "_" + index + FileUtil.getFileSuffix(oldFile.getPath());
        File newFile = new File(newName);
        if (newFile.exists()) {
            log.warn("该文件名存在");
            index += 1;
            rename(oldFile, filePrefix, index);
        }
        if (oldFile.renameTo(newFile)) {
            log.info("已重命名");
        } else {
            log.error("文件重命名失败");
        }
        index += 1;
        return index;
    }


    /**
     * 通过url下载文件到本地或者服务器
     *
     * @param url 文件url
     * @return 返回 文件名
     */
    public static String downloadFileByUrl(String url, String filePath) {
        fileExist(filePath);
        File file = new File(url);
        try {
            // 建立链接
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            InputStream inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            FileOutputStream fileOut = new FileOutputStream(filePath + file.getName());
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }
        return file.getName();
    }


    /**
     * 调用7z进行解压
     *
     * @param filePath 文件路径
     * @throws Exception 错误
     */
    public static String unZip(String filePath) throws Exception {
        File zipFile = new File(filePath);
        if (!zipFile.exists()) {
            log.error(filePath + "不存在...");
            throw new Exception();
        }
        File zipExeFile = new File(CustomConfig.zipExe);
        String unzipPath = zipFile.getParent() + File.separator + System.currentTimeMillis();
        // zipFile.getAbsolutePath() 指压缩的文件路径, unzipPath 指解压到哪儿的文件路径
        String exec = String.format(zipExeFile + " x \"" + zipFile.getAbsolutePath() + "\" -o\"" + unzipPath) + "\"";
        runTime(exec);
        return unzipPath;
    }


    /**
     * 调用7z压缩文件并加密
     *
     * @param filePath 文件路径
     * @throws IOException
     */
    public static String zip(String filePath) throws Exception {
        File zipFile = new File(filePath);
        if (!zipFile.exists()) {
            log.error(filePath + "不存在...");
            throw new Exception();
        }
        File zipExeFile = new File(CustomConfig.zipExe);
        String fileName = System.currentTimeMillis() + FileUtil.getFileSuffix(filePath);
        String zipPath = CustomConfig.fileSave + File.separator + fileName;
        // zipPath 指压缩到哪儿的文件路径  filePath 指要进行压缩的文件路径
        String exec = String.format(zipExeFile + " a " + zipPath + " " + filePath + "\\*" + " -pnanfengluojin");
        runTime(exec);
        return fileName;
    }

    private static void runTime(String exec) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(exec);
        InputStream is = process.getInputStream();
        BufferedReader bd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bd.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        is.close();
        bd.close();
        process.destroy();
    }


}
