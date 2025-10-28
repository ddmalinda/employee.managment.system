package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.dto.DeparmentDto;
import abc.company.plt.employee.managment.system.model.Deparment;
import abc.company.plt.employee.managment.system.service.DeparmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deparments")
public class DeparmentController {

    @Autowired
    private DeparmentService deparmentService;

    //Get All departments
    @GetMapping
    public ResponseEntity<List<DeparmentDto>> getAllDeparmentDetails(){
        List<DeparmentDto> deparments=deparmentService.getAllDeparments();
        return ResponseEntity.ok(deparments);
    }

    //get deparment by id
    @GetMapping("/{id}")
    public ResponseEntity<Deparment> getDeparmentDetailsById(@PathVariable Long id){
        return deparmentService.getDeparmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //add deparment
    @PostMapping
    public ResponseEntity<Deparment> addDepartment(@RequestBody Deparment deparment){
        Deparment addDeparment= deparmentService.addDeparment(deparment);
        return new ResponseEntity<>(addDeparment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deparment> updateDeparment(@PathVariable Long id,@RequestBody Deparment deparmentDetails){
        try{
            Deparment updatedDeparment = deparmentService.updateDepartment(id,deparmentDetails);
            return ResponseEntity.ok(updatedDeparment);
        }catch(RuntimeException ex){
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeparment(@PathVariable Long id){
        if(deparmentService.getDeparmentById(id).isPresent()){
            deparmentService.deletDeparment(id);
            return ResponseEntity.noContent().build(); //retun 204
        }else{
            return ResponseEntity.notFound().build(); // return 404
        }
    }
}
