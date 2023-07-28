package com.freitas.employeehub.dto;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.freitas.employeehub.entity.EmployeeModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmployeeDTO(EmployeeModel entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
