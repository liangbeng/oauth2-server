package org.wzp.oauth2.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/23 15:49
 */
public interface UserEasyExcelWriteService {

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


}
