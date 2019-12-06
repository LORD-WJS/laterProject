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
public class Chapter implements Serializable {
    @Id
    private String id;
    private String title;
    private String name;
    private String audio;
    @Column(name = "audioSize")
    private String audioSize;
    private String time;//音频时长
    @Column(name = "createDate")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @Column(name = "albumId")
    private String albumId;


}
