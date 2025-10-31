package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.dto.DepartmentDTO;
import abc.company.plt.employee.managment.system.model.Department;
import abc.company.plt.employee.managment.system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //Get All departments
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartmentDetails() {
        try {
            List<DepartmentDTO> Departments = departmentService.getAllDepartments();
            return ResponseEntity.ok(Departments);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //get Department by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentDetailsById(@PathVariable Long id) {
        try {
            return departmentService.getDepartmentById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //add Department
    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department departmentDetails) {
        if (departmentDetails.getDepartmentName() == null ||
                departmentDetails.getDepartmentName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request
        } else {
            Department addDepartment = departmentService.addDepartment(departmentDetails);
            return new ResponseEntity<>(addDepartment, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department DepartmentDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, DepartmentDetails);
            return ResponseEntity.ok(updatedDepartment);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            if (departmentService.getDepartmentById(id).isPresent()) {
                departmentService.deleteDepartment(id);
                return ResponseEntity.noContent().build(); //retun 204
            } else {
                return ResponseEntity.notFound().build(); // return 404
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
