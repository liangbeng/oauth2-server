package org.wzp.oauth2.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.wzp.oauth2.entity.User;

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
     * 将excel数据导入表中
     *
     * @param sheet     excel数据
     * @param rowNumber 总行数
     */
    void setUserExcelData(Sheet sheet, int rowNumber);
}
