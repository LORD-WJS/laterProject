package com.wjs.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Banner {
    @Id
    private String id;
    private String title;
    private String name;
    private String cover;
    private String href;
    @Column(name = "createDate")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private String introduce;
    private String status;
}
