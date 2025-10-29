package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.model.Deparment;
import abc.company.plt.employee.managment.system.model.Emplyee;
import abc.company.plt.employee.managment.system.model.Project;
import abc.company.plt.employee.managment.system.repository.EmplyeeRepository;
import abc.company.plt.employee.managment.system.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmplyeeRepository emplyeeRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project testProject;
    private Deparment testDepartment;
    private Emplyee testEmployee;

    @BeforeEach
    void setUp() {
        testDepartment = new Deparment();
        testDepartment.setDeparmentID(1L);
        testDepartment.setDepartmentName("IT Department");

        testEmployee = new Emplyee();
        testEmployee.setEmplyeeID(1L);
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");

        testProject = new Project();
        testProject.setProjectID(1L);
        testProject.setProjectName("Project Alpha");
        testProject.setDeparment(testDepartment);
        testProject.setEmployees(new HashSet<>());
    }

    @Test
    void getProjectID_WhenExists_ShouldReturnProject() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        // When
        Optional<Project> result = projectService.getProjectID(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getProjectID()).isEqualTo(1L);
        assertThat(result.get().getProjectName()).isEqualTo("Project Alpha");

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void getProjectID_WhenNotExists_ShouldReturnEmpty() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Project> result = projectService.getProjectID(999L);

        // Then
        assertThat(result).isEmpty();
        verify(projectRepository, times(1)).findById(999L);
    }

    @Test
    void getALLProject_ShouldReturnAllProjects() {
        // Given
        Project project2 = new Project();
        project2.setProjectID(2L);
        project2.setProjectName("Project Beta");

        List<Project> projects = Arrays.asList(testProject, project2);
        when(projectRepository.findAll()).thenReturn(projects);

        // When
        List<Project> result = projectService.getALLProject();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProjectName()).isEqualTo("Project Alpha");
        assertThat(result.get(1).getProjectName()).isEqualTo("Project Beta");

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void getALLProject_WhenEmpty_ShouldReturnEmptyList() {
        // Given
        when(projectRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Project> result = projectService.getALLProject();

        // Then
        assertThat(result).isEmpty();
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void addProject_ShouldSaveAndReturnProject() {
        // Given
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        // When
        Project result = projectService.addProject(testProject);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProjectID()).isEqualTo(1L);
        assertThat(result.getProjectName()).isEqualTo("Project Alpha");

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void updateProject_WhenExists_ShouldUpdateAndReturnProject() {
        // Given
        Project updatedDetails = new Project();
        updatedDetails.setProjectName("Project Alpha Updated");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        // When
        Project result = projectService.updateProject(1L, updatedDetails);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProjectName()).isEqualTo("Project Alpha Updated");

        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void updateProject_WhenNotExists_ShouldThrowException() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        Project updatedDetails = new Project();
        updatedDetails.setProjectName("Updated Name");

        // When & Then
        assertThatThrownBy(() -> projectService.updateProject(999L, updatedDetails))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Project not found with id: 999");

        verify(projectRepository, times(1)).findById(999L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void deleteProject_ShouldCallRepositoryDelete() {
        // Given
        Long projectId = 1L;
        doNothing().when(projectRepository).deleteById(projectId);

        // When
        projectService.deleteProject(projectId);

        // Then
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void assignEmployeeToProject_WhenBothExist_ShouldAssignEmployee() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(emplyeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        // When
        Project result = projectService.assignEmployeeToProject(1L, 1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmployees()).contains(testEmployee);

        verify(projectRepository, times(1)).findById(1L);
        verify(emplyeeRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void assignEmployeeToProject_WhenProjectNotExists_ShouldThrowException() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> projectService.assignEmployeeToProject(999L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Project not found with id: 999");

        verify(projectRepository, times(1)).findById(999L);
        verify(emplyeeRepository, never()).findById(any());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void assignEmployeeToProject_WhenEmployeeNotExists_ShouldThrowException() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(emplyeeRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> projectService.assignEmployeeToProject(1L, 999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Employee not found with id: 999");

        verify(projectRepository, times(1)).findById(1L);
        verify(emplyeeRepository, times(1)).findById(999L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void assignEmployeeToProject_WhenEmployeeAlreadyAssigned_ShouldNotDuplicate() {
        // Given
        testProject.getEmployees().add(testEmployee);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(emplyeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        // When
        Project result = projectService.assignEmployeeToProject(1L, 1L);

        // Then
        assertThat(result.getEmployees()).hasSize(1); // Set should prevent duplicates
        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void removeEmployeeFromProject_WhenBothExist_ShouldRemoveEmployee() {
        // Given
        testProject.getEmployees().add(testEmployee);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(emplyeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        // When
        Project result = projectService.removeEmployeeFromProject(1L, 1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmployees()).doesNotContain(testEmployee);

        verify(projectRepository, times(1)).findById(1L);
        verify(emplyeeRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void removeEmployeeFromProject_WhenProjectNotExists_ShouldThrowException() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> projectService.removeEmployeeFromProject(999L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Project not found with id: 999");

        verify(projectRepository, times(1)).findById(999L);
        verify(emplyeeRepository, never()).findById(any());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void removeEmployeeFromProject_WhenEmployeeNotExists_ShouldThrowException() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(emplyeeRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> projectService.removeEmployeeFromProject(1L, 999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Employee not found with id: 999");

        verify(projectRepository, times(1)).findById(1L);
        verify(emplyeeRepository, times(1)).findById(999L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void removeEmployeeFromProject_WhenEmployeeNotInProject_ShouldStillWork() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(emplyeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        // When
        Project result = projectService.removeEmployeeFromProject(1L, 1L);

        // Then
        assertThat(result.getEmployees()).doesNotContain(testEmployee);
        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void getProjectEmployees_WhenProjectExists_ShouldReturnEmployees() {
        // Given
        Emplyee employee2 = new Emplyee();
        employee2.setEmplyeeID(2L);
        employee2.setEmail("jane.smith@example.com");

        testProject.getEmployees().add(testEmployee);
        testProject.getEmployees().add(employee2);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        // When
        Set<Emplyee> result = projectService.getProjectEmployees(1L);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).contains(testEmployee, employee2);

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void getProjectEmployees_WhenProjectHasNoEmployees_ShouldReturnEmptySet() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        // When
        Set<Emplyee> result = projectService.getProjectEmployees(1L);

        // Then
        assertThat(result).isEmpty();
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void getProjectEmployees_WhenProjectNotExists_ShouldThrowException() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> projectService.getProjectEmployees(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Project not found with id: 999");

        verify(projectRepository, times(1)).findById(999L);
    }
}
