package com.wjs.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Master implements Serializable {
    @Id
    @ExcelProperty(value = "id")
    private String id;
    @ExcelProperty(value = "真名")
    private String realname;
    @Transient
    private String name;
    @ExcelProperty(value = "face")
    private String face;
    @ExcelProperty(value = "status")
    private String status;
    @ExcelProperty(value = "nickName")
    @Column(name = "nickName")
    private String nickName;
    @ExcelProperty(value = "createDate")
    @Column(name = "createDate")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;

}
