package com.freitas.employeehub.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity @Table(name = "tb_employee")
public class EmployeeModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    @Column(nullable = false)
    private String name;    
    
    @Column(nullable = false, unique = true)
    private String email;    
    
    @Column(nullable = false)
    private String password; 
    
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;   
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}