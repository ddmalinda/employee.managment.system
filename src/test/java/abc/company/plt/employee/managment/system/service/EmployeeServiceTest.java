package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.model.Department;
import abc.company.plt.employee.managment.system.model.Employee;
import abc.company.plt.employee.managment.system.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setDepartmentID(1L);
        testDepartment.setDepartmentName("IT Department");

        testEmployee = new Employee();
        testEmployee.setEmployeeID(1L);
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setPassword("password123");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment(testDepartment);
    }

    @Test
    void getEmployeeById_WhenExists_ShouldReturnEmployee() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));

        // When
        Optional<Employee> result = employeeService.getEmployeeById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmployeeID()).isEqualTo(1L);
        assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get().getFirstName()).isEqualTo("John");

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_WhenNotExists_ShouldReturnEmpty() {
        // Given
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Employee> result = employeeService.getEmployeeById(999L);

        // Then
        assertThat(result).isEmpty();
        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    void getAllEmployeeList_ShouldReturnAllEmployees() {
        // Given
        Employee employee2 = new Employee();
        employee2.setEmployeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setFirstName("Jane");

        List<Employee> employees = Arrays.asList(testEmployee, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        // When
        List<Employee> result = employeeService.getAllEmployeeList();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get(1).getEmail()).isEqualTo("jane.smith@example.com");

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getAllEmployeeList_WhenEmpty_ShouldReturnEmptyList() {
        // Given
        when(employeeRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Employee> result = employeeService.getAllEmployeeList();

        // Then
        assertThat(result).isEmpty();
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void deleteEmployeeById_ShouldCallRepositoryDelete() {
        // Given
        Long employeeId = 1L;
        doNothing().when(employeeRepository).deleteById(employeeId);

        // When
        employeeService.deleteEmployeeById(employeeId);

        // Then
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void addEmployee_ShouldSaveAndReturnEmployee() {
        // Given
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);

        // When
        Employee result = employeeService.addEmployee(testEmployee);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmployeeID()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getFirstName()).isEqualTo("John");

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_WhenExists_ShouldUpdateAndReturnEmployee() {
        // Given
        Employee updatedDetails = new Employee();
        updatedDetails.setEmail("john.updated@example.com");
        updatedDetails.setPassword("newpassword");
        updatedDetails.setFirstName("John");
        updatedDetails.setLastName("Updated");
        updatedDetails.setDepartment(testDepartment);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);

        // When
        Employee result = employeeService.updateEmployee(1L, updatedDetails);

        // Then
        assertThat(result).isNotNull();
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_WhenNotExists_ShouldThrowException() {
        // Given
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        Employee updatedDetails = new Employee();
        updatedDetails.setEmail("john.updated@example.com");

        // When & Then
        assertThatThrownBy(() -> employeeService.updateEmployee(999L, updatedDetails))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Employee not found with id: 999");

        verify(employeeRepository, times(1)).findById(999L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void getEmployeeListByDepartmentId_ShouldReturnEmployeesInDepartment() {
        // Given
        Employee employee2 = new Employee();
        employee2.setEmployeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setDepartment(testDepartment);

        List<Employee> departmentEmployees = Arrays.asList(testEmployee, employee2);
        when(employeeRepository.findByDepartmentDepartmentID(1L)).thenReturn(departmentEmployees);

        // When
        List<Employee> result = employeeService.getEmployeeListByDepartmentId(1L);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDepartment().getDepartmentID()).isEqualTo(1L);
        assertThat(result.get(1).getDepartment().getDepartmentID()).isEqualTo(1L);

        verify(employeeRepository, times(1)).findByDepartmentDepartmentID(1L);
    }

    @Test
    void getEmployeeListByDepartmentId_WhenNoEmployees_ShouldReturnEmptyList() {
        // Given
        when(employeeRepository.findByDepartmentDepartmentID(999L)).thenReturn(Arrays.asList());

        // When
        List<Employee> result = employeeService.getEmployeeListByDepartmentId(999L);

        // Then
        assertThat(result).isEmpty();
        verify(employeeRepository, times(1)).findByDepartmentDepartmentID(999L);
    }

    @Test
    void addEmployee_WithNullEmail_ShouldStillSave() {
        // Given
        Employee employeeWithNullEmail = new Employee();
        employeeWithNullEmail.setFirstName("Test");
        employeeWithNullEmail.setLastName("User");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeWithNullEmail);

        // When
        Employee result = employeeService.addEmployee(employeeWithNullEmail);

        // Then
        assertThat(result).isNotNull();
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_ShouldUpdateAllFields() {
        // Given
        Department newDepartment = new Department();
        newDepartment.setDepartmentID(2L);
        newDepartment.setDepartmentName("HR Department");

        Employee updatedDetails = new Employee();
        updatedDetails.setEmail("newemail@example.com");
        updatedDetails.setPassword("newpass123");
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Smith");
        updatedDetails.setDepartment(newDepartment);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Employee result = employeeService.updateEmployee(1L, updatedDetails);

        // Then
        assertThat(result.getEmail()).isEqualTo("newemail@example.com");
        assertThat(result.getPassword()).isEqualTo("newpass123");
        assertThat(result.getFirstName()).isEqualTo("Jane");
        assertThat(result.getLastName()).isEqualTo("Smith");
        assertThat(result.getDepartment().getDepartmentID()).isEqualTo(2L);

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(testEmployee);
    }
}
