package org.wzp.oauth2.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.wzp.oauth2.entity.User;

import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/7 13:20
 */
public interface ExcelService {

    boolean getUserExcelData(List<User> list);

    void setUserExcelData(Sheet sheet, int lastRowNum);
}
