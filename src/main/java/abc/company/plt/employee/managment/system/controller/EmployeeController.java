package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Employee;
import abc.company.plt.employee.managment.system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //Add new employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //List All Employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees=employeeService.getAllEmployeeList();
        return ResponseEntity.ok(employees);
    }

    //Specific employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok) //if found, return 200
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Employee [cite: 16, 32]
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    // Delete Employee [cite: 17, 33]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        // Basic check if employee exists before deleting
        if (employeeService.getEmployeeById(id).isPresent()) {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content on success
        } else {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    //List employees by department ID
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>>getEmployeesByDepartment(@PathVariable Long departmentId){
        List<Employee> employees =employeeService.getEmployeeListByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }
}
