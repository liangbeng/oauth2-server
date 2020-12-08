package org.wzp.oauth2.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.config.CustomConfig;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.mapper.UserMapper;
import org.wzp.oauth2.service.ExcelService;
import org.wzp.oauth2.util.StringUtil;
import org.wzp.oauth2.util.excel.ExcelData;
import org.wzp.oauth2.util.excel.ExcelUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/7 13:20
 */
@Service
public class ExcelServiceImpl extends BaseConfig implements ExcelService {

    @Resource
    private UserMapper userMapper;


    public boolean getUserExcelData(List<User> list) {
        List<List<Object>> rows = new ArrayList();
        for (User user : list) {
            List<Object> row = new ArrayList();
            row.add(user.getId());
            row.add(user.getUsername());
            row.add(user.getEnable().equals(true) ? "是" : "否");
            row.add(user.getName());
            row.add(user.getSex());
            row.add(user.getPhone());
            row.add(user.getProvince());
            row.add(user.getCity());
            rows.add(row);
        }
        ExcelData data = new ExcelData();
        List<String> titles = new ArrayList();
        titles.add("id");
        titles.add("用户名");
        titles.add("是否激活");
        titles.add("姓名");
        titles.add("性别");
        titles.add("电话");
        titles.add("省");
        titles.add("市");
        data.setTitles(titles);
        data.setRows(rows);
        try {
            String filename = ExcelUtils.ExcelConstant.FILE_NAME;
            ExcelUtils.exportExcel(response, filename, data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public void setUserExcelData(Sheet sheet, int lastRowNum) {
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(6);
            if (StringUtil.isEmpty(cell) || StringUtil.isEmpty(cell1) || StringUtil.isEmpty(cell2) || StringUtil.isEmpty(cell3)
                    || StringUtil.isEmpty(cell4) || StringUtil.isEmpty(cell5) || StringUtil.isEmpty(cell6)) {
                continue;
            }
            String username = cell.getStringCellValue();
            if (userMapper.selectByUsername(username) != null) {
                continue;
            }
            String name = cell1.getStringCellValue();
            String enable = ("是".equals(cell2.getStringCellValue())) ? "true" : "false";
            String sex = cell3.getStringCellValue();
            String phone = cell4.getStringCellValue();
            String province = cell5.getStringCellValue();
            String city = cell6.getStringCellValue();
            User user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(CustomConfig.defaultPassword));
            user.setEnable(Boolean.valueOf(enable));
            user.setName(name);
            user.setSex(sex);
            user.setPhone(phone);
            user.setProvince(province);
            user.setCity(city);
            userMapper.insertSelective(user);
        }
    }
}
