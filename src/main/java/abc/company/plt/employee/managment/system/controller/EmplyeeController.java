package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Emplyee;
import abc.company.plt.employee.managment.system.service.EmplyeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emplyee")
@CrossOrigin(origins = "*")
public class EmplyeeController {
    @Autowired
    private EmplyeeService emplyeeService;

    @GetMapping
    public ResponseEntity<List<Emplyee>>getEmplyeeForDepartment(@PathVariable Long departmentId){
        List<Emplyee> emplyees =emplyeeService.getEmplyeeListByDepartmentId(departmentId);
        return ResponseEntity.ok(emplyees);
    }
}
