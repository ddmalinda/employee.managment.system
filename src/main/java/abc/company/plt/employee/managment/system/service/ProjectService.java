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
        return projectRepository.findById(projectID);
    }

    //find all projects
    public List<Project> getALLProject(){
        return projectRepository.findAll();
    }

    //add project
    public Project addProject(Project project){
        return projectRepository.save(project);
    }

    //Update project
    public Project updateProject(Long projectId,Project projectDetails){
            Project project =projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
            //update project
            project.setProjectName(projectDetails.getProjectName());
            return projectRepository.save(project);
    }

    //delete project
    public void deleteProject(Long projectid){
        projectRepository.deleteById(projectid);
    }

    // Assign an employee to a project
    @Transactional
    public Project assignEmployeeToProject(Long projectId, Long employeeId) {
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
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        return project.getEmployees();
    }
}
