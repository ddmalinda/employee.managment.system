package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Emplyee;
import abc.company.plt.employee.managment.system.service.EmplyeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmplyeeController {
    @Autowired
    private EmplyeeService emplyeeService;

    //Add new Emplyee
    @PostMapping
    public ResponseEntity<Emplyee> addEmplyee(@RequestBody Emplyee emplyee){
        Emplyee saveEmplyee = emplyeeService.addEmplyee(emplyee);
        return new ResponseEntity<>(saveEmplyee, HttpStatus.CREATED);
    }

    //List All Emplyess
    @GetMapping
    public ResponseEntity<List<Emplyee>> getAllEmplyees(){
        List<Emplyee> emplyees=emplyeeService.getAllEmplyeeList();
        return ResponseEntity.ok(emplyees);
    }

    //Specific Emplyee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Emplyee> getEmplyeeById(@PathVariable Long id){
        return emplyeeService.getEmplyeebyId(id)
                .map(ResponseEntity::ok) //if found, return 200
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Employee [cite: 16, 32]
    @PutMapping("/{id}")
    public ResponseEntity<Emplyee> updateEmplyee(@PathVariable Long id, @RequestBody Emplyee emplyeeDetails) {
        try {
            Emplyee updatedEmplyee = emplyeeService.updateEmplyee(id, emplyeeDetails);
            return ResponseEntity.ok(updatedEmplyee);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    // Delete Employee [cite: 17, 33]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmplyee(@PathVariable Long id) {
        // Basic check if employee exists before deleting
        if (emplyeeService.getEmplyeebyId(id).isPresent()) {
            emplyeeService.deleteEmpyeeById(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content on success
        } else {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    //List emplyees by deparmrnt ID
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Emplyee>>getEmplyeesByDepartment(@PathVariable Long departmentId){
        List<Emplyee> emplyees =emplyeeService.getEmplyeeListByDepartmentId(departmentId);
        return ResponseEntity.ok(emplyees);
    }
}
