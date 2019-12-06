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
public class Course implements Serializable {
    @Id
    private String id;
    private String name;
    @Column(name = "userId")
    private String userId;
    private String type;
    @Column(name = "createDate")
    private Date createDate;

}
