package abc.company.plt.employee.managment.system.controller;

import abc.company.plt.employee.managment.system.model.Employee;
import abc.company.plt.employee.managment.system.model.Project;
import abc.company.plt.employee.managment.system.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee testEmployee;
    private Project testProject;

    @BeforeEach
    void setUp(){


    }

}

