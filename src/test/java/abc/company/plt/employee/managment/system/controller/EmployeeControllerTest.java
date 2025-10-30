package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Department;
import abc.company.plt.employee.managment.system.model.Employee;
import abc.company.plt.employee.managment.system.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void addEmployee_ShouldReturnCreatedEmployee() throws Exception {
        // Given
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(testEmployee);

        // When & Then
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeID", is(1)))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));

        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeList() throws Exception {
        // Given
        Employee employee2 = new Employee();
        employee2.setEmployeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");

        List<Employee> employees = Arrays.asList(testEmployee, employee2);
        when(employeeService.getAllEmployeeList()).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeID", is(1)))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[1].employeeID", is(2)))
                .andExpect(jsonPath("$[1].email", is("jane.smith@example.com")));

        verify(employeeService, times(1)).getAllEmployeeList();
    }

    @Test
    void getEmployeeById_WhenExists_ShouldReturnEmployee() throws Exception {
        // Given
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(testEmployee));

        // When & Then
        mockMvc.perform(get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeID", is(1)))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.firstName", is("John")));

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void getEmployeeById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(employeeService.getEmployeeById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployeeById(999L);
    }

    @Test
    void updateEmployee_WhenExists_ShouldReturnUpdatedEmployee() throws Exception {
        // Given
        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmployeeID(1L);
        updatedEmployee.setEmail("john.updated@example.com");
        updatedEmployee.setFirstName("John");
        updatedEmployee.setLastName("Updated");

        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(updatedEmployee);

        // When & Then
        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john.updated@example.com")))
                .andExpect(jsonPath("$.lastName", is("Updated")));

        verify(employeeService, times(1)).updateEmployee(eq(1L), any(Employee.class));
    }

    @Test
    void updateEmployee_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(employeeService.updateEmployee(eq(999L), any(Employee.class)))
                .thenThrow(new RuntimeException("Employee not found with id: 999"));

        // When & Then
        mockMvc.perform(put("/api/employees/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).updateEmployee(eq(999L), any(Employee.class));
    }

    @Test
    void deleteEmployee_WhenExists_ShouldReturnNoContent() throws Exception {
        // Given
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(testEmployee));
        doNothing().when(employeeService).deleteEmployeeById(1L);

        // When & Then
        mockMvc.perform(delete("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).getEmployeeById(1L);
        verify(employeeService, times(1)).deleteEmployeeById(1L);
    }

    @Test
    void deleteEmployee_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(employeeService.getEmployeeById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/api/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployeeById(999L);
        verify(employeeService, never()).deleteEmployeeById(999L);
    }

    @Test
    void getEmployeesByDepartment_ShouldReturnEmployeeList() throws Exception {
        // Given
        Employee employee2 = new Employee();
        employee2.setEmployeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setFirstName("Jane");
        employee2.setDepartment(testDepartment);

        List<Employee> departmentEmployees = Arrays.asList(testEmployee, employee2);
        when(employeeService.getEmployeeListByDepartmentId(1L)).thenReturn(departmentEmployees);

        // When & Then
        mockMvc.perform(get("/api/employees/department/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeID", is(1)))
                .andExpect(jsonPath("$[1].employeeID", is(2)));

        verify(employeeService, times(1)).getEmployeeListByDepartmentId(1L);
    }

    @Test
    void getEmployeesByDepartment_WhenNone_ShouldReturnEmptyList() throws Exception {
        // Given
        when(employeeService.getEmployeeListByDepartmentId(999L)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/employees/department/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(employeeService, times(1)).getEmployeeListByDepartmentId(999L);
    }
}
