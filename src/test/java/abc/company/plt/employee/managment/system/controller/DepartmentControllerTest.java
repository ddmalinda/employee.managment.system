package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.dto.DepartmentDTO;
import abc.company.plt.employee.managment.system.model.Department;
import abc.company.plt.employee.managment.system.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;


    private ObjectMapper objectMapper;
    private Department department;
    private DepartmentDTO departmentDTO;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();

        //setup test data
        department = new Department();
        department.setDepartmentID(1L);
        department.setDepartmentName("Engineering");

        departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentID(1L);
        departmentDTO.setDepartmentName("Engineering");
    }

    @Test
    void testGetAllDepartments_Success() throws Exception {
        // Arrange
        DepartmentDTO department2 = new DepartmentDTO();
        department2.setDepartmentID(2L);
        department2.setDepartmentName("HR");

        List<DepartmentDTO> departments = Arrays.asList(departmentDTO, department2);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].departmentID", is(1)))
                .andExpect(jsonPath("$[0].departmentName", is("Engineering")))
                .andExpect(jsonPath("$[1].departmentID", is(2)))
                .andExpect(jsonPath("$[1].departmentName", is("HR")));

        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void testGetAllDepartments_EmptyList() throws Exception{
        //Given
        when(departmentService.getAllDepartments()).thenReturn(Arrays.asList());

        //When & Then
        mockMvc.perform(get("/api/departments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(0)));
        verify(departmentService,times(1)).getAllDepartments();
    }

    @Test
    void testGetDepartmentById_Success() throws Exception{
        //Given
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(department));

        //When & Then
        mockMvc.perform(get("/api/departments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentID",is(1)))
                .andExpect(jsonPath("$.departmentName",is("Engineering")));

        verify(departmentService,times(1)).getDepartmentById(1L);
    }


}

