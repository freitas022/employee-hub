package com.freitas.employeehub.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public Page<EmployeeDTO> findAll(Pageable pageable) {
        Page<EmployeeModel> result = employeeRepository.findAll(pageable);
        return result.map(EmployeeDTO::new);
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