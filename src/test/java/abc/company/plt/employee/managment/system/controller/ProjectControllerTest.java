package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Department;
import abc.company.plt.employee.managment.system.model.employee;
import abc.company.plt.employee.managment.system.model.Project;
import abc.company.plt.employee.managment.system.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    private Project testProject;
    private Department testDepartment;
    private employee testEmployee;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setDepartmentID(1L);
        testDepartment.setDepartmentName("IT Department");

        testEmployee = new employee();
        testEmployee.setemployeeID(1L);
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");

        testProject = new Project();
        testProject.setProjectID(1L);
        testProject.setProjectName("Project Alpha");
        testProject.setDepartment(testDepartment);
        testProject.setEmployees(new HashSet<>());
    }

    @Test
    void getProjectById_WhenExists_ShouldReturnProject() throws Exception {
        // Given
        when(projectService.getProjectID(1L)).thenReturn(Optional.of(testProject));

        // When & Then
        mockMvc.perform(get("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectID", is(1)))
                .andExpect(jsonPath("$.projectName", is("Project Alpha")));

        verify(projectService, times(1)).getProjectID(1L);
    }

    @Test
    void getProjectById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(projectService.getProjectID(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/projects/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).getProjectID(999L);
    }

    @Test
    void getAllProjects_ShouldReturnProjectList() throws Exception {
        // Given
        Project project2 = new Project();
        project2.setProjectID(2L);
        project2.setProjectName("Project Beta");

        List<Project> projects = Arrays.asList(testProject, project2);
        when(projectService.getALLProject()).thenReturn(projects);

        // When & Then
        mockMvc.perform(get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].projectID", is(1)))
                .andExpect(jsonPath("$[0].projectName", is("Project Alpha")))
                .andExpect(jsonPath("$[1].projectID", is(2)))
                .andExpect(jsonPath("$[1].projectName", is("Project Beta")));

        verify(projectService, times(1)).getALLProject();
    }

    @Test
    void getAllProjects_WhenEmpty_ShouldReturnEmptyList() throws Exception {
        // Given
        when(projectService.getALLProject()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(projectService, times(1)).getALLProject();
    }

    @Test
    void addProject_ShouldReturnCreatedProject() throws Exception {
        // Given
        when(projectService.addProject(any(Project.class))).thenReturn(testProject);

        // When & Then
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProject)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.projectID", is(1)))
                .andExpect(jsonPath("$.projectName", is("Project Alpha")));

        verify(projectService, times(1)).addProject(any(Project.class));
    }

    @Test
    void updateProject_WhenExists_ShouldReturnUpdatedProject() throws Exception {
        // Given
        Project updatedProject = new Project();
        updatedProject.setProjectID(1L);
        updatedProject.setProjectName("Project Alpha Updated");

        when(projectService.updateProject(eq(1L), any(Project.class))).thenReturn(updatedProject);

        // When & Then
        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName", is("Project Alpha Updated")));

        verify(projectService, times(1)).updateProject(eq(1L), any(Project.class));
    }

    @Test
    void updateProject_WhenNotExists_ShouldThrowException() throws Exception {
        // Given
        when(projectService.updateProject(eq(999L), any(Project.class)))
                .thenThrow(new RuntimeException("Project not found with id: 999"));

        // When & Then
        mockMvc.perform(put("/api/projects/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProject)))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).updateProject(eq(999L), any(Project.class));
    }

    @Test
    void deleteProject_WhenExists_ShouldReturnNoContent() throws Exception {
        // Given
        when(projectService.getProjectID(1L)).thenReturn(Optional.of(testProject));
        doNothing().when(projectService).deleteProject(1L);

        // When & Then
        mockMvc.perform(delete("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).getProjectID(1L);
        verify(projectService, times(1)).deleteProject(1L);
    }

    @Test
    void deleteProject_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(projectService.getProjectID(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/api/projects/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).getProjectID(999L);
        verify(projectService, never()).deleteProject(999L);
    }

    @Test
    void assignEmployeeToProject_ShouldReturnUpdatedProject() throws Exception {
        // Given
        testProject.getEmployees().add(testEmployee);
        when(projectService.assignEmployeeToProject(1L, 1L)).thenReturn(testProject);

        // When & Then
        mockMvc.perform(put("/api/projects/1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectID", is(1)));

        verify(projectService, times(1)).assignEmployeeToProject(1L, 1L);
    }

    @Test
    void assignEmployeeToProject_WhenProjectNotExists_ShouldThrowException() throws Exception {
        // Given
        when(projectService.assignEmployeeToProject(999L, 1L))
                .thenThrow(new RuntimeException("Project not found with id: 999"));

        // When & Then
        mockMvc.perform(put("/api/projects/999/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).assignEmployeeToProject(999L, 1L);
    }

    @Test
    void assignEmployeeToProject_WhenEmployeeNotExists_ShouldThrowException() throws Exception {
        // Given
        when(projectService.assignEmployeeToProject(1L, 999L))
                .thenThrow(new RuntimeException("Employee not found with id: 999"));

        // When & Then
        mockMvc.perform(put("/api/projects/1/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).assignEmployeeToProject(1L, 999L);
    }

    @Test
    void removeEmployeeFromProject_ShouldReturnUpdatedProject() throws Exception {
        // Given
        when(projectService.removeEmployeeFromProject(1L, 1L)).thenReturn(testProject);

        // When & Then
        mockMvc.perform(delete("/api/projects/1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectID", is(1)));

        verify(projectService, times(1)).removeEmployeeFromProject(1L, 1L);
    }

    @Test
    void removeEmployeeFromProject_WhenProjectNotExists_ShouldThrowException() throws Exception {
        // Given
        when(projectService.removeEmployeeFromProject(999L, 1L))
                .thenThrow(new RuntimeException("Project not found with id: 999"));

        // When & Then
        mockMvc.perform(delete("/api/projects/999/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).removeEmployeeFromProject(999L, 1L);
    }

    @Test
    void removeEmployeeFromProject_WhenEmployeeNotExists_ShouldThrowException() throws Exception {
        // Given
        when(projectService.removeEmployeeFromProject(1L, 999L))
                .thenThrow(new RuntimeException("Employee not found with id: 999"));

        // When & Then
        mockMvc.perform(delete("/api/projects/1/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).removeEmployeeFromProject(1L, 999L);
    }

    @Test
    void getProjectEmployees_ShouldReturnEmployeeSet() throws Exception {
        // Given
        employee employee2 = new employee();
        employee2.setemployeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setFirstName("Jane");

        Set<employee> employees = new HashSet<>(Arrays.asList(testEmployee, employee2));
        when(projectService.getProjectEmployees(1L)).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/api/projects/1/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(projectService, times(1)).getProjectEmployees(1L);
    }

    @Test
    void getProjectEmployees_WhenNoEmployees_ShouldReturnEmptySet() throws Exception {
        // Given
        when(projectService.getProjectEmployees(1L)).thenReturn(new HashSet<>());

        // When & Then
        mockMvc.perform(get("/api/projects/1/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(projectService, times(1)).getProjectEmployees(1L);
    }

    @Test
    void getProjectEmployees_WhenProjectNotExists_ShouldThrowException() throws Exception {
        // Given
        when(projectService.getProjectEmployees(999L))
                .thenThrow(new RuntimeException("Project not found with id: 999"));

        // When & Then
        mockMvc.perform(get("/api/projects/999/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(projectService, times(1)).getProjectEmployees(999L);
    }
}
