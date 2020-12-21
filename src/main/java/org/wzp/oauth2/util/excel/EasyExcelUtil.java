package org.wzp.oauth2.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/16 13:36
 */
@Slf4j
public class EasyExcelUtil {

    //excel的sheet集合
    private List<WriteSheet> sheets;

    //excel存放的文件夹
    private String excelSavePath;

    //默认每个sheet存储的行数
    private static final int defaultSheetNum = 500000;


    /**
     * 对象创建，生成excel时，文件默认存放的文件夹"G:/oauth-server/excel"
     */
    public EasyExcelUtil() {
    }


    /**
     * 对象创建，生成excel时，excel文件指定存放的文件夹，文件夹可以多级 folderName:"aa/bb/cc"
     *
     * @param folderName
     */
    public EasyExcelUtil(String folderName) {
        excelSavePath = folderName;
    }


    /**
     * excel表格存放路径
     *
     * @param excelName
     * @return
     */
    private String route(String excelName) {
        String excelPath = excelSavePath + excelName;
        File file = new File(excelPath);
        File file1 = new File(file.getParent());
        if (!file1.exists()) {
            file1.mkdirs();
        }
        return excelPath;
    }


    /**
     * 创建excel和sheet,创建时可以指定sheet 数量
     *
     * @param excelName
     * @param clazz
     * @param numSheet
     * @return
     */
    public ExcelWriter create(String excelName, Class clazz, int numSheet) {
        ExcelWriter excelWriter = EasyExcel.write(route(excelName), clazz.asSubclass(clazz)).build();
        createSheets(numSheet);
        return excelWriter;
    }


    /**
     * 创建excel和sheet
     * 根据数据量计算需要创建几个sheet，默认每个sheet放500000数据
     *
     * @param excelName
     * @param clazz
     * @return
     * @throws Exception
     */
    public ExcelWriter create(String excelName, Long totalNum, Class clazz) {
        ExcelWriter excelWriter = EasyExcel.write(route(excelName), clazz.asSubclass(clazz)).build();
        Long sheetNumber = (totalNum % defaultSheetNum) > 0 ? (totalNum / defaultSheetNum) + 1 : (totalNum / defaultSheetNum);
        createSheets(sheetNumber);
        return excelWriter;
    }


    /**
     * 写数据到excel,仅使用一个sheet,不可用于五百万以上数据
     *
     * @param excelWriter
     * @param list
     */
    public void write(ExcelWriter excelWriter, List list) {
        excelWriter.write(list, sheets.get(0));
    }


    /**
     * 写数据到excel
     *
     * @param excelWriter
     * @param list        每一次的数据
     * @param resize      动态调整大小
     */
    public void write(ExcelWriter excelWriter, List list, int resize) {
        int index = resize / (defaultSheetNum + 1);
        excelWriter.write(list, sheets.get(index));
    }


    /**
     * 写完数据关闭(finish 有关流操作)，必须的操作
     *
     * @param excelWriter
     */
    public void finish(ExcelWriter excelWriter) {
        excelWriter.finish();
    }


    /**
     * 创建指定数量的sheet
     *
     * @param num sheet的数量
     */
    private void createSheets(long num) {
        sheets = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            WriteSheet sheet = EasyExcel.writerSheet(i, "sheet" + i).build();
            sheets.add(sheet);
        }
    }


    /**
     * 将excel生成到服务器后通过url下载
     *
     * @param response
     */
    public void downloadExcel(HttpServletResponse response) {
        try {
            //获取服务器文件
            File file = new File("G:\\oauth-server\\excel\\系统用户表1608172347120.xlsx");
            InputStream ins = new FileInputStream(file);
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = ins.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
            os.close();
            ins.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}