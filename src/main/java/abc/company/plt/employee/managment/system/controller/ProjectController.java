package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Emplyee;
import abc.company.plt.employee.managment.system.model.Project;
import abc.company.plt.employee.managment.system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        return projectService.getProjectID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Project>> getProjectById(){
        List<Project> projects = projectService.getALLProject();
        return ResponseEntity.ok(projects);
    }

    // Create a new project
    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody Project projectDetails){
        Project project = projectService.addProject(projectDetails);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }
    //update project
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId,@RequestBody Project proejctDetails){
        Project project =projectService.updateProject(projectId,proejctDetails);
        return ResponseEntity.ok(project);
    }

    //detele project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        // Basic check if employee exists before deleting
        if(projectService.getProjectID(id).isPresent()){
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content on success
        } else {
            return ResponseEntity.notFound().build(); // Return 404
        }
    }

    // Assign employee to project
    @PutMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<Project> assignEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        Project updatedProject = projectService.assignEmployeeToProject(projectId, employeeId);
        return ResponseEntity.ok(updatedProject);
    }

    // Remove employee from project
    @DeleteMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<Project> removeEmployeeFromProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        Project updatedProject = projectService.removeEmployeeFromProject(projectId, employeeId);
        return ResponseEntity.ok(updatedProject);
    }

    // Get all employees in a project
    @GetMapping("/{projectId}/employees")
    public ResponseEntity<Set<Emplyee>> getProjectEmployees(@PathVariable Long projectId) {
        Set<Emplyee> employees = projectService.getProjectEmployees(projectId);
        return ResponseEntity.ok(employees);
    }
}