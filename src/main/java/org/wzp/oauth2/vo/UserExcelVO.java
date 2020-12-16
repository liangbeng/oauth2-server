package org.wzp.oauth2.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/16 13:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
public class UserExcelVO {

    @ExcelProperty("账号")
    @ColumnWidth(20)
    private String username;

    @ExcelProperty("密码")
    @ColumnWidth(70)
    private String password;




}
