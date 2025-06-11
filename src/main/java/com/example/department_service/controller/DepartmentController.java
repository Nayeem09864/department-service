package com.example.department_service.controller;

import com.example.department_service.model.Department;
import com.example.department_service.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger LOGGGER = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department) {
        LOGGGER.info("Adding department {}", department);
        return departmentRepository.addDepartment(department);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        LOGGGER.info("Getting all departments");
        return departmentRepository.findAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        LOGGGER.info("Getting department by id {}", id);
        return departmentRepository.findDepartmentById(id);
    }
}
