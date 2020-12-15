package org.wzp.oauth2.util;

import lombok.extern.slf4j.Slf4j;
import org.wzp.oauth2.config.CustomConfig;

import java.io.*;

@Slf4j
public class ZipUtil {

    /**
     * 调用7z进行解压
     *
     * @param filePath
     * @throws Exception
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
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(exec);
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
        }
        process.waitFor();
        is.close();
        reader.close();
        process.destroy();
        return unzipPath;
    }


    /**
     * 压缩文件并加密
     *
     * @param filePath
     * @throws IOException
     */
    public static String Zip(String filePath) throws Exception {
        File zipFile = new File(filePath);
        if (!zipFile.exists()) {
            log.error(filePath + "不存在...");
            throw new Exception();
        }
        File zipExeFile = new File(CustomConfig.zipExe);
        String fileName = System.currentTimeMillis() + FileUtil.getFileSuffix(filePath);
        String zipPath = CustomConfig.fileSave + File.separator + fileName;
        // zipPath 指压缩到哪儿的文件路径  filePath 指要进行压缩的文件路径
        String exec = String.format(zipExeFile + " a " + zipPath + " " + filePath + "\\*" + " -pkuye");
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(exec);
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
        }
        while (fileName == null) {
        }
        process.waitFor();
        is.close();
        reader.close();
        process.destroy();
        return fileName;
    }


}
