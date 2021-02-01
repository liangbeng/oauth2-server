package org.wzp.oauth2.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.wzp.oauth2.config.CustomConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/16 11:48
 */
@Slf4j
public class FileUtil {

    private static final int BF_SIZE = 1024;


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
     * 获取文件名
     *
     * @param url 文件路径 G:/oauth-server/excel/1608883380852.xlsx
     * @return 1608883380852.xlsx
     */
    private static String getFileName(String url) {
        if (ObjUtil.isEmpty(url) || !url.contains("/")) {
            log.error("请检查传递参数是否正确");
            return "";
        }
        return url.substring(url.lastIndexOf("/") + 1);
    }


    /**
     * 获取文件名
     *
     * @param fileName 123.zip
     * @return 123 不包括分隔符
     */
    public static String getFilePrefix(String fileName) {
        if (ObjUtil.isEmpty(fileName)) {
            log.error("文件名不存在!!!");
            return "";
        }
        return ObjUtil.strPrefix(fileName, ".", 0);
    }


    /**
     * 获取文件名后缀
     *
     * @param fileName 123.zip
     * @return .zip 包括分隔符
     */
    public static String getFileSuffix(String fileName) {
        if (ObjUtil.isEmpty(fileName)) {
            log.error("文件名不存在!!!");
            return "";
        }
        return ObjUtil.strSuffix(fileName, ".", 0);
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
     * 读取文件
     *
     * @param fileName 待读文件名
     * @return
     * @throws IOException
     */
    public static String readFile(String fileName) {
        StringBuffer buf = new StringBuffer();
        try {
            FileInputStream input = new FileInputStream(fileName);
            FileChannel channel = input.getChannel();
            CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
            CharBuffer cBuf = CharBuffer.allocate(BF_SIZE);
            ByteBuffer bBuf = ByteBuffer.allocate(BF_SIZE);
            while (channel.read(bBuf) != -1) {
                bBuf.flip();
                decoder.decode(bBuf, cBuf, false);
                bBuf.clear();
                buf.append(cBuf.array(), 0, cBuf.position());
                cBuf.compact();
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }


    /**
     * 写文件
     *
     * @param filename   目标文件
     * @param content    待写入内容
     * @param additional 是否是追加写入
     */
    public static void writeFile(String filename, String content, Boolean additional) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            Path path = Paths.get(filename);
            BufferedWriter writer;
            if (additional) {
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            } else {
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            }
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 复制文件
     *
     * @param sourceFile 待复制文件
     * @param targetFile 待写入文件
     * @throws IOException
     */
    public static void copyFile(String sourceFile, String targetFile) {
        try {
            File file = new File(sourceFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(sourceFile);
            FileChannel inChannel = fis.getChannel();
            FileOutputStream fos = new FileOutputStream(targetFile);
            FileChannel outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
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
     * @param url  文件网络路径
     * @param path 本地存储文件夹路径
     * @return 返回文件本地存储全路径
     */
    public static String downloadFileAsUrl(String url, String path) {
        String fileName = getFileName(url);
        fileExist(path);
        String filePath = path + fileName;
        int index = 0;
        String returnValue = download(url, filePath, index);
        if (returnValue.equals("404")) {
            return "404";
        }
        return filePath;
    }


    /**
     * @param url      文件网络路径
     * @param filePath 文件本地存储路径
     * @param index    重试次数
     * @return 返回结果 200 表示下载成功，404表示下载失败，网路文件路径不正确
     */
    public static String download(String url, String filePath, int index) {
        fileExist(filePath);
        File file = new File(url);
        try {
            // 建立连接
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            if (conn.getContentLength() == -1 || conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                if (index < 3) {
                    index = index + 1;
                    log.warn("建立连接失败，第{}次重试开始......", index);
                    Thread.sleep(1000);
                    download(url, filePath, index);
                } else {
                    return "404";
                }
            }
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            InputStream inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            FileOutputStream fos = new FileOutputStream(filePath + file.getName());
            byte[] buf = new byte[1024];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                fos.write(buf, 0, length);
                length = bis.read(buf);
            }
            fos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            log.error("抛出异常!!!");
            e.printStackTrace();
        }
        log.info("下载成功!!!");
        return "200";
    }


    /**
     * 从本地服务器下载文件
     *
     * @param file
     * @param response
     */
    public static void downloadFile(File file, HttpServletResponse response) {
        OutputStream os = null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            String contentType = Files.probeContentType(Paths.get(file.getAbsolutePath()));
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("utf-8"), "ISO8859-1"));
            FileInputStream fileInputStream = new FileInputStream(file);
            WritableByteChannel writableByteChannel = Channels.newChannel(os);
            FileChannel fileChannel = fileInputStream.getChannel();
            fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
            fileChannel.close();
            os.flush();
            writableByteChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件的关闭放在finally中
        finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            log.error("{}不存在...", filePath);
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
            log.error("{}不存在...", filePath);
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
            log.info(line);
        }
        process.waitFor();
        is.close();
        bd.close();
        process.destroy();
    }


}
