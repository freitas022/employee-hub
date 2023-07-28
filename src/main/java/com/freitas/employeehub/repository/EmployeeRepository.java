package com.freitas.employeehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freitas.employeehub.entity.EmployeeModel;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

}