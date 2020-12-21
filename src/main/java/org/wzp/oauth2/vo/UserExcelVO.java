package org.wzp.oauth2.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/16 13:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 22)
@ContentStyle(borderLeft = BorderStyle.THIN, borderTop = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderBottom = BorderStyle.THIN)
public class UserExcelVO {

    @ExcelProperty("id")
    @ColumnWidth(5)
    private Long id;

    @ExcelProperty("账号")
    @ColumnWidth(20)
    private String username;

    @ExcelProperty("密码")
    @ColumnWidth(70)
    private String password;


}
