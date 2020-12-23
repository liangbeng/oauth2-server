package org.wzp.oauth2.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.wzp.oauth2.config.CustomConfig;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.service.UserEasyExcelWriteService;
import org.wzp.oauth2.util.excel.EasyExcelUtil;
import org.wzp.oauth2.vo.UserExcelVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/23 15:51
 */
@Service
public class UserEasyExcelWriteServiceImpl implements UserEasyExcelWriteService {

    @Resource
    private UserServiceImpl userServiceImpl;


    private static final String savePath = CustomConfig.fileSave;

    /**
     * 默认每次从数据库取十万的数据
     */
    private static final Integer excelRows = 10000;

    /**
     * 默认每个sheet存储一百万的数据
     */
    private static final int defaultSheetNum = 1000000;


    //----------------------------- easyExcel导出 -------------------------------

    @Override
    public boolean excelExport(Integer totalNum, String fileName) {
        EasyExcelUtil easyExcelUtil = new EasyExcelUtil(savePath);
        ExcelWriter excelWriter = null;
        try {
            excelWriter = easyExcelUtil.create(fileName, totalNum, UserExcelVO.class);
            List<UserExcelVO> list = new ArrayList<>();
            //分多次导出 为了降低导出过程中的内存资源
            //根据数据总数据量和每次拿的数据量计算出需要拿几次数据
            Integer number = (totalNum % excelRows) > 0 ? (totalNum / excelRows) + 1 : (totalNum / excelRows);
            //判断是多sheet导出还是单sheet导出
            int count = 0;
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
            /*if (totalNum <= defaultSheetNum) {
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
                int count = 0;
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
            }*/
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


    /**
     * 直接通过浏览器下载到客户端
     *
     * @param response response
     * @param totalNum 数据量
     * @param filename
     */
    @Override
    public boolean excelDownload(HttpServletResponse response, Integer totalNum, String filename) {
        try {
            new EasyExcelUtil().getResponse(response, filename);
            OutputStream outputStream = response.getOutputStream();
            List<UserExcelVO> list = new ArrayList<>();
            long number = (totalNum % excelRows) > 0 ? (totalNum / excelRows) + 1 : (totalNum / excelRows);
            //如果总数据量大于单sheet的存放数量，则多sheet导出
            int count = 0;
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            for (int i = 1; i <= number; i++) {
                IPage<User> page = userServiceImpl.getUserByPage(i, excelRows);
                page.getRecords().forEach(user -> {
                    list.add(new UserExcelVO(user.getId(), user.getUsername(), user.getPassword()));
                });
                //判断写到哪一个sheet中
                count += list.size();
                int sheetNumber = count / (defaultSheetNum) + 1;
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetNumber, "sheet" + (sheetNumber))
                        .head(UserExcelVO.class)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                excelWriter.write(list, writeSheet);
                //清空list，避免数据重复
                list.clear();
            }
            //刷新流
            excelWriter.finish();
            /*if (totalNum <= defaultSheetNum) {
                IPage<User> page = userServiceImpl.getUserByPage(1, totalNum);
                page.getRecords().forEach(user -> {
                    list.add(new UserExcelVO(user.getId(), user.getUsername(), user.getPassword()));
                });
                EasyExcel.write(outputStream, UserExcelVO.class)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .sheet("sheet").doWrite(list);
                list.clear();
            } else {
                int count = 0;
                ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
                for (int i = 1; i <= number; i++) {
                    IPage<User> page = userServiceImpl.getUserByPage(i, excelRows);
                    page.getRecords().forEach(user -> {
                        list.add(new UserExcelVO(user.getId(), user.getUsername(), user.getPassword()));
                    });
                    //判断写到哪一个sheet中
                    count += list.size();
                    int sheetNumber = count / (defaultSheetNum) + 1;
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetNumber, "sheet" + (sheetNumber))
                            .head(UserExcelVO.class)
                            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                    excelWriter.write(list, writeSheet);
                    //清空list，避免数据重复
                    list.clear();
                }
                //刷新流
                excelWriter.finish();
            }*/
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
