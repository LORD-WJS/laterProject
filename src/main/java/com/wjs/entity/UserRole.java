package com.wjs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRole implements Serializable {
    private String id;
    private String userId;
    private String roleId;
}
