package com.wjs.entity;

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
public class Counter implements Serializable {
    @Id
    private String id;
    private String name;
    private Integer count;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "userId")
    private String userId;
    @Column(name = "courseId")
    private String courseId;


}
