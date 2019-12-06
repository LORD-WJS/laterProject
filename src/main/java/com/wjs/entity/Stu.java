package com.wjs.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by HIAPAD on 2019/11/29.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stu {
    @ExcelProperty(value = "ID")
    private String id;
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "年龄")
    private Integer age;
    @ExcelProperty(value = "生日")
    private Date birth;
}
