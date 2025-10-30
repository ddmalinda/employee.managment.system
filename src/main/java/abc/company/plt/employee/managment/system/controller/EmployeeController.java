package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.employee;
import abc.company.plt.employee.managment.system.service.employeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class employeeController {
    @Autowired
    private employeeService employeeService;

    //Add new employee
    @PostMapping
    public ResponseEntity<employee> addemployee(@RequestBody employee employee){
        employee saveemployee = employeeService.addemployee(employee);
        return new ResponseEntity<>(saveemployee, HttpStatus.CREATED);
    }

    //List All Emplyess
    @GetMapping
    public ResponseEntity<List<employee>> getAllemployees(){
        List<employee> employees=employeeService.getAllemployeeList();
        return ResponseEntity.ok(employees);
    }

    //Specific employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<employee> getemployeeById(@PathVariable Long id){
        return employeeService.getemployeebyId(id)
                .map(ResponseEntity::ok) //if found, return 200
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Employee [cite: 16, 32]
    @PutMapping("/{id}")
    public ResponseEntity<employee> updateemployee(@PathVariable Long id, @RequestBody employee employeeDetails) {
        try {
            employee updatedemployee = employeeService.updateemployee(id, employeeDetails);
            return ResponseEntity.ok(updatedemployee);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    // Delete Employee [cite: 17, 33]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteemployee(@PathVariable Long id) {
        // Basic check if employee exists before deleting
        if (employeeService.getemployeebyId(id).isPresent()) {
            employeeService.deleteEmpyeeById(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content on success
        } else {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    //List employees by deparmrnt ID
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<employee>>getemployeesByDepartment(@PathVariable Long departmentId){
        List<employee> employees =employeeService.getemployeeListByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }
}
