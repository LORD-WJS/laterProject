package com.wjs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Attention implements Serializable {
    @Id
    private String id;
    @Column(name = "masterId")
    private String masterId;
    @Column(name = "userId")
    private String userId;

}
