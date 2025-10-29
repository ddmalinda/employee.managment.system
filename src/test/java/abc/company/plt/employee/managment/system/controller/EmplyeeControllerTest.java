package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Deparment;
import abc.company.plt.employee.managment.system.model.Emplyee;
import abc.company.plt.employee.managment.system.service.EmplyeeService;
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

@WebMvcTest(EmplyeeController.class)
class EmplyeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmplyeeService emplyeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Emplyee testEmplyee;
    private Deparment testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = new Deparment();
        testDepartment.setDeparmentID(1L);
        testDepartment.setDepartmentName("IT Department");

        testEmplyee = new Emplyee();
        testEmplyee.setEmplyeeID(1L);
        testEmplyee.setEmail("john.doe@example.com");
        testEmplyee.setPassword("password123");
        testEmplyee.setFirstName("John");
        testEmplyee.setLastName("Doe");
        testEmplyee.setDeparment(testDepartment);
    }

    @Test
    void addEmplyee_ShouldReturnCreatedEmployee() throws Exception {
        // Given
        when(emplyeeService.addEmplyee(any(Emplyee.class))).thenReturn(testEmplyee);

        // When & Then
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmplyee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.emplyeeID", is(1)))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));

        verify(emplyeeService, times(1)).addEmplyee(any(Emplyee.class));
    }

    @Test
    void getAllEmplyees_ShouldReturnEmployeeList() throws Exception {
        // Given
        Emplyee employee2 = new Emplyee();
        employee2.setEmplyeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");

        List<Emplyee> employees = Arrays.asList(testEmplyee, employee2);
        when(emplyeeService.getAllEmplyeeList()).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].emplyeeID", is(1)))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[1].emplyeeID", is(2)))
                .andExpect(jsonPath("$[1].email", is("jane.smith@example.com")));

        verify(emplyeeService, times(1)).getAllEmplyeeList();
    }

    @Test
    void getEmplyeeById_WhenExists_ShouldReturnEmployee() throws Exception {
        // Given
        when(emplyeeService.getEmplyeebyId(1L)).thenReturn(Optional.of(testEmplyee));

        // When & Then
        mockMvc.perform(get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emplyeeID", is(1)))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.firstName", is("John")));

        verify(emplyeeService, times(1)).getEmplyeebyId(1L);
    }

    @Test
    void getEmplyeeById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(emplyeeService.getEmplyeebyId(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(emplyeeService, times(1)).getEmplyeebyId(999L);
    }

    @Test
    void updateEmplyee_WhenExists_ShouldReturnUpdatedEmployee() throws Exception {
        // Given
        Emplyee updatedEmployee = new Emplyee();
        updatedEmployee.setEmplyeeID(1L);
        updatedEmployee.setEmail("john.updated@example.com");
        updatedEmployee.setFirstName("John");
        updatedEmployee.setLastName("Updated");

        when(emplyeeService.updateEmplyee(eq(1L), any(Emplyee.class))).thenReturn(updatedEmployee);

        // When & Then
        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john.updated@example.com")))
                .andExpect(jsonPath("$.lastName", is("Updated")));

        verify(emplyeeService, times(1)).updateEmplyee(eq(1L), any(Emplyee.class));
    }

    @Test
    void updateEmplyee_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(emplyeeService.updateEmplyee(eq(999L), any(Emplyee.class)))
                .thenThrow(new RuntimeException("Emplyee not found with id: 999"));

        // When & Then
        mockMvc.perform(put("/api/employees/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmplyee)))
                .andExpect(status().isNotFound());

        verify(emplyeeService, times(1)).updateEmplyee(eq(999L), any(Emplyee.class));
    }

    @Test
    void deleteEmplyee_WhenExists_ShouldReturnNoContent() throws Exception {
        // Given
        when(emplyeeService.getEmplyeebyId(1L)).thenReturn(Optional.of(testEmplyee));
        doNothing().when(emplyeeService).deleteEmpyeeById(1L);

        // When & Then
        mockMvc.perform(delete("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(emplyeeService, times(1)).getEmplyeebyId(1L);
        verify(emplyeeService, times(1)).deleteEmpyeeById(1L);
    }

    @Test
    void deleteEmplyee_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(emplyeeService.getEmplyeebyId(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/api/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(emplyeeService, times(1)).getEmplyeebyId(999L);
        verify(emplyeeService, never()).deleteEmpyeeById(999L);
    }

    @Test
    void getEmplyeesByDepartment_ShouldReturnEmployeeList() throws Exception {
        // Given
        Emplyee employee2 = new Emplyee();
        employee2.setEmplyeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setFirstName("Jane");
        employee2.setDeparment(testDepartment);

        List<Emplyee> departmentEmployees = Arrays.asList(testEmplyee, employee2);
        when(emplyeeService.getEmplyeeListByDepartmentId(1L)).thenReturn(departmentEmployees);

        // When & Then
        mockMvc.perform(get("/api/employees/department/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].emplyeeID", is(1)))
                .andExpect(jsonPath("$[1].emplyeeID", is(2)));

        verify(emplyeeService, times(1)).getEmplyeeListByDepartmentId(1L);
    }

    @Test
    void getEmplyeesByDepartment_WhenNone_ShouldReturnEmptyList() throws Exception {
        // Given
        when(emplyeeService.getEmplyeeListByDepartmentId(999L)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/employees/department/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(emplyeeService, times(1)).getEmplyeeListByDepartmentId(999L);
    }
}
