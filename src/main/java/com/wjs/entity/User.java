package com.wjs.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Id
    private String id;
    private String username;
    private String name;
    private String password;
    private String salt;
    private String phone;
    private String face;
    private String sex;
    private String location;
    private String signature;//签名
    @Column(name = "createDate")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @Column(name = "lastLoginTime")
    @JSONField(format = "yyyy-MM-dd")
    private Date lastLoginTime;
    private String status;

}
