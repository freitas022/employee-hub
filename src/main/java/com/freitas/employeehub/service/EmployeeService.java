package com.freitas.employeehub.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.freitas.employeehub.dto.EmployeeDTO;
import com.freitas.employeehub.entity.EmployeeModel;
import com.freitas.employeehub.exception.NotFoundException;
import com.freitas.employeehub.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeDTO> findAll(int page, int pageSize) {
        Pageable request = PageRequest.of(page, pageSize);
        Page<EmployeeModel> result = employeeRepository.findAll(request);
        return result.stream().map(EmployeeDTO::new).toList();
    }

    public EmployeeDTO findById(Long id) {
        EmployeeModel employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        return new EmployeeDTO(employee);
    }

    public EmployeeDTO insert(EmployeeDTO dto) {
        EmployeeModel employee = new EmployeeModel();
        toDtoFromEntity(dto, employee);
        employee = employeeRepository.save(employee);
        return new EmployeeDTO(employee);
    }

    public EmployeeDTO update(Long id, EmployeeDTO request) {
        return employeeRepository.findById(id)
            .map(existingEmp -> {
                existingEmp.setName(request.getName());
                existingEmp.setEmail(request.getEmail());
                existingEmp.setPassword(request.getPassword());
                return employeeRepository.save(existingEmp);
            }).map(EmployeeDTO::new).orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(Long id) {
        employeeRepository.delete(employeeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(id)));
    }

    public void toDtoFromEntity(EmployeeDTO dto, EmployeeModel entity) {
        BeanUtils.copyProperties(dto, entity);
    }
}