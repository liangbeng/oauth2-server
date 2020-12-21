package org.wzp.oauth2.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import org.wzp.oauth2.util.DateUtil;
import org.wzp.oauth2.util.StringUtil;
import org.wzp.oauth2.util.excel.EasyExcelUtil;
import org.wzp.oauth2.util.excel.ExcelData;
import org.wzp.oauth2.util.excel.ExcelUtils;
import org.wzp.oauth2.vo.UserExcelVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
    @Resource
    private UserServiceImpl userServiceImpl;

    private String savePath = CustomConfig.fileSave;

    //默认每次从数据库取十万的数据
    private Integer excelRows = 100000;

    //默认每个sheet存储的行数
    private static final int defaultSheetNum = 500000;


    @Override
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


    @Override
    public boolean excelExport(Long totalNum, String fileName) {
        int count = 0;
        EasyExcelUtil easyExcelUtil = null;
        ExcelWriter excelWriter = null;
        try {
            //创建文件夹名
            easyExcelUtil = new EasyExcelUtil(savePath);
            excelWriter = easyExcelUtil.create(fileName, totalNum, UserExcelVO.class);
            List<UserExcelVO> list = new ArrayList<>();
            //分多次导出 为了降低导出过程中的内存资源
            //根据数据总数据量和每次拿的数据量计算出需要拿几次数据
            Long number = (totalNum % excelRows) > 0 ? (totalNum / excelRows) + 1 : (totalNum / excelRows);
            //判断是多sheet导出还是单sheet导出
            if (totalNum <= defaultSheetNum) {
                //单sheet导出
                for (int i = 1; i <= number; i++) {
                    IPage<User> page = userServiceImpl.getUserByPage(i, excelRows);
                    page.getRecords().forEach(user -> {
                        list.add(new UserExcelVO(user.getId(), user.getUsername(), user.getPassword()));
                    });
                    easyExcelUtil.write(excelWriter, list);
                    //必须clear,否则数据会重复
                    list.clear();
                }
            } else {
                //多sheet导出
                for (int i = 1; i <= number; i++) {
                    IPage<User> page = userServiceImpl.getUserByPage(i, excelRows);
                    page.getRecords().forEach(user -> {
                        list.add(new UserExcelVO(user.getId(), user.getUsername(), user.getPassword()));
                    });
                    //count 将控制插入哪一个sheet
                    count += list.size();
                    easyExcelUtil.write(excelWriter, list, count);
                    list.clear();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (null != excelWriter) {
                easyExcelUtil.finish(excelWriter);
            }
        }
        return true;
    }


    public void excelDownload(HttpServletResponse response, Long totalNum) {
        //直接通过浏览器下载到客户端
        try {
            OutputStream outputStream = response.getOutputStream();
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "系统用戶表" + DateUtil.sysTime() + ".xlsx");
            //设置类型
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            List<UserExcelVO> list = new ArrayList<>();
            long number = (totalNum % excelRows) > 0 ? (totalNum / excelRows) + 1 : (totalNum / excelRows);
            //如果总数据量多于10万，分页导出
            if (number > 1) {
                ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
                for (int i = 1; i <= number; i++) {
                    IPage<User> page = userServiceImpl.getUserByPage(i, excelRows);
                    page.getRecords().forEach(user -> {
                        list.add(new UserExcelVO(user.getId(), user.getUsername(), user.getPassword()));
                    });
                    WriteSheet writeSheet = EasyExcel.writerSheet(i, "系统用戶表" + (i))
                            .head(UserExcelVO.class)
                            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                    excelWriter.write(list, writeSheet);
                    //清空list，避免数据重复
                    list.clear();
                }
                //刷新流
                excelWriter.finish();
            } else {
                IPage<User> page = userServiceImpl.getUserByPage(1, excelRows);
                page.getRecords().forEach(user -> {
                    list.add(new UserExcelVO(user.getId(), user.getUsername(), user.getPassword()));
                });
                EasyExcel.write(outputStream, UserExcelVO.class)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .sheet("系统用戶表").doWrite(list);
            }
            outputStream.flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
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
