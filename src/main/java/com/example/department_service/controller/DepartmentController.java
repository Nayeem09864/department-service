package com.example.department_service.controller;

import com.example.department_service.client.EmployeeClient;
import com.example.department_service.model.Department;
import com.example.department_service.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentRepository departmentRepository;
    private final EmployeeClient employeeClient;

    public DepartmentController(DepartmentRepository departmentRepository, EmployeeClient employeeClient) {
        this.departmentRepository = departmentRepository;
        this.employeeClient = employeeClient;
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department) {
        log.info("Adding department {}", department);
        return departmentRepository.addDepartment(department);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        log.info("Getting all departments");
        return departmentRepository.findAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        log.info("Getting department by id {}", id);
        return departmentRepository.findDepartmentById(id);
    }

    @GetMapping("/with-employees")
    public List<Department> getDepartmentsWithEmployees(){
        log.info("Getting department with employees");
        List<Department> departments = departmentRepository.findAllDepartments();
        departments.forEach(department -> {
            department.setEmployees(
                    employeeClient.findAllEmployeesByDepartmentId(department.getId()
                    )
            );
        });
        return departments;
    }
}
