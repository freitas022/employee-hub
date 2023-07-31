package com.freitas.employeehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freitas.employeehub.dto.EmployeeDTO;
import com.freitas.employeehub.service.EmployeeService;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> findAll(@PageableDefault(page = 0, sort = "id",
                                                         direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(employeeService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> insert(@RequestBody EmployeeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.insert(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeDTO request) {
        return ResponseEntity.ok().body(employeeService.update(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
