package org.wzp.oauth2.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.wzp.oauth2.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/7 13:20
 */
public interface ExcelService {

    /**
     * 将表数据导出为excel
     *
     * @param list 用户列表
     * @return 返回值 true false
     */
    boolean getUserExcelData(List<User> list);


    /**
     * 将表数据导出为excel
     *
     * @param total    数量
     * @param fileName excel表名
     * @return 返回值 true false
     */
    boolean excelExport(Integer total, String fileName);


    /**
     * 通过客户端浏览器直接下载
     *
     * @param response response
     * @param totalNum 数据量
     * @param fileName 文件名
     * @return 返回值 true false
     */
    boolean excelDownload(HttpServletResponse response, Integer totalNum, String fileName);


    /**
     * 将excel数据导入表中
     *
     * @param sheet     excel数据
     * @param rowNumber 总行数
     */
    void setUserExcelData(Sheet sheet, int rowNumber);
}
