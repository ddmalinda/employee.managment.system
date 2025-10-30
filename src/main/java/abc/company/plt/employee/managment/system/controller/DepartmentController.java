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
    private DepartmentService DepartmentService;

    //Get All departments
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartmentDetails(){
        List<DepartmentDTO> Departments=DepartmentService.getAllDepartments();
        return ResponseEntity.ok(Departments);
    }

    //get Department by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentDetailsById(@PathVariable Long id){
        return DepartmentService.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //add Department
    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department Department){
        Department addDepartment= DepartmentService.addDepartment(Department);
        return new ResponseEntity<>(addDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id,@RequestBody Department DepartmentDetails){
        try{
            Department updatedDepartment = DepartmentService.updateDepartment(id,DepartmentDetails);
            return ResponseEntity.ok(updatedDepartment);
        }catch(RuntimeException ex){
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id){
        if(DepartmentService.getDepartmentById(id).isPresent()){
            DepartmentService.deletDepartment(id);
            return ResponseEntity.noContent().build(); //retun 204
        }else{
            return ResponseEntity.notFound().build(); // return 404
        }
    }
}
