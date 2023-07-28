package com.freitas.employeehub.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freitas.employeehub.dto.EmployeeDTO;
import com.freitas.employeehub.entity.EmployeeModel;
import com.freitas.employeehub.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO insert(EmployeeDTO dto) {
        dto.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        dto.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        EmployeeModel employee = new EmployeeModel();
        toDtoFromEntity(dto, employee);
        employee = employeeRepository.save(employee);
        return new EmployeeDTO(employee);
    }

    public void toDtoFromEntity(EmployeeDTO dto, EmployeeModel entity) {
        BeanUtils.copyProperties(dto, entity);
    }
}