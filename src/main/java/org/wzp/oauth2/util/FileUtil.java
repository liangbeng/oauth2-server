package org.wzp.oauth2.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/16 11:48
 */
@Slf4j
public class FileUtil {


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
                //输出文件父路径
                String strParentDirectory = dir.getParent();
                //输出文件的父文件名
                String ParentDirectory = dir.getParentFile().getName();
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
     * @param filePath
     * @return
     */
    public static boolean delFolder(String filePath) {
        boolean flag = true;
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                File[] filePaths = file.listFiles();
                for (File f : filePaths) {
                    if (f.isFile()) {
                        f.delete();
                    }
                    if (f.isDirectory()) {
                        String fPath = f.getPath();
                        delFolder(fPath);
                        f.delete();
                    }
                }
                //删除当前目录
                if (file.isDirectory()) {
                    file.delete();
                }
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
     * @return 123
     */
    public static String getFilePrefix(String fileName) {
        return StringUtil.strPrefix(fileName, ".", 0);
    }


    /**
     * 获取文件名后缀
     *
     * @param fileName 123.zip
     * @return .zip
     */
    public static String getFileSuffix(String fileName) {
        return StringUtil.strSuffix(fileName, ".", 0);
    }


    /**
     * 复制文件
     *
     * @param srcFolder
     * @param destFolder
     */
    public static void move(File srcFolder, File destFolder) {
        File[] fileArray = srcFolder.listFiles();
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
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
     * @param path
     * @return
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
     * @param filePrefix
     * @param filePath
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


}
