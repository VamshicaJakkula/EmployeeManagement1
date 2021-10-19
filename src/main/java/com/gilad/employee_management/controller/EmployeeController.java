package com.gilad.employee_management.controller;

import com.gilad.employee_management.exception.ResourceNotFoundException;
import com.gilad.employee_management.model.Emp;
import com.gilad.employee_management.model.Employee;
import com.gilad.employee_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/employees")
    public List<Map<String, Object>> getAllEmployees() {
    	String query = "select * from employee_details";
    	List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(query);
    	System.out.println(employeeList.size());
    	return employeeList;
    }

    @PostMapping("/employees")
    public void createEmployee(@Valid @RequestBody Employee employee) {
//        return ResponseEntity.ok(employeeRepository.save(employeeRepository.save(employee)));
    	String query = " insert into employee_details (id, first_name) values (+3,"+ employee.getFirstName() +") ";
    	jdbcTemplate.execute(query);
    	//Map<String, Object> employee1 = jdbcTemplate.queryForList(query).size() > 0  ? jdbcTemplate.queryForMap(query) : null;
        //return employee;
    }

    @GetMapping("/employees/{emailId}")
    public Map<String, Object> getEmployeeById(@PathVariable String emailId) {
    	String query = "select * from employee_details where email_id = ? ";
    	Map<String, Object> employee = jdbcTemplate.queryForList(query, emailId).size() > 0  ? jdbcTemplate.queryForMap(query, emailId) : null;
        return employee;
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with the id - " + id + " not exist"));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with the id - " + id + " not exist"));
        if (employee == null)
            throw new ResourceNotFoundException("id-" + id);
        employeeRepository.delete(employee);
        Map<String, Boolean> res = new HashMap<>();
        res.put("delete", Boolean.TRUE);
        return ResponseEntity.ok(res);
    }
    
}
