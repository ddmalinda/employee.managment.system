package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.model.Employee;
import abc.company.plt.employee.managment.system.model.Project;
import abc.company.plt.employee.managment.system.repository.EmployeeRepository;
import abc.company.plt.employee.managment.system.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository  projectRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    //find by poject id
    public Optional<Project>  getProjectID(Long projectID){
        if(projectID==null || projectID<=0){
            throw new IllegalArgumentException("Invalid Project ID");
        }
        return projectRepository.findById(projectID);
    }

    //find all projects
    public List<Project> getALLProject(){
        return projectRepository.findAll();
    }

    //add project
    public Project addProject(Project project){
        if (project == null || project.getProjectName() == null || project.getProjectName().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
        return projectRepository.save(project);
    }

    //Update project
    public Project updateProject(Long projectId,Project projectDetails){
        if(projectId==null || projectId<=0){
            throw new IllegalArgumentException("Invalid Project ID");
        }
            Project project =projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
            //update project
            project.setProjectName(projectDetails.getProjectName());
            return projectRepository.save(project);
    }

    //delete project
    public void deleteProject(Long projectID){
        if(projectID==null || projectID<=0){
            throw new IllegalArgumentException("Invalid Project ID");
        }
        projectRepository.deleteById(projectID);
    }

    // Assign an employee to a project
    @Transactional
    public Project assignEmployeeToProject(Long projectId, Long employeeId) {
        if(projectId==null || projectId<=0){
            throw new IllegalArgumentException("Invalid Project ID");
        }

        if(employeeId==null || employeeId<=0){
            throw new IllegalArgumentException("Invalid Employee ID");
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        project.getEmployees().add(employee);

        return projectRepository.save(project);
    }

    // Remove an employee from a project
    @Transactional
    public Project removeEmployeeFromProject(Long projectId, Long employeeId) {
        if(projectId==null || projectId<=0){
            throw new IllegalArgumentException("Invalid Project ID");
        }

        if(employeeId==null || employeeId<=0){
            throw new IllegalArgumentException("Invalid Employee ID");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        project.getEmployees().remove(employee);

        return projectRepository.save(project);
    }

    // Get all employees in a project
    @Transactional
    public Set<Employee> getProjectEmployees(Long projectId) {
        if(projectId==null || projectId<=0){
            throw new IllegalArgumentException("Invalid Project ID");
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        return project.getEmployees();
    }
}
