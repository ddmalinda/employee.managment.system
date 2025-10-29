package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.model.Deparment;
import abc.company.plt.employee.managment.system.model.Emplyee;
import abc.company.plt.employee.managment.system.repository.EmplyeeRepository;
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
class EmplyeeServiceTest {

    @Mock
    private EmplyeeRepository emplyeeRepository;

    @InjectMocks
    private EmplyeeService emplyeeService;

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
    void getEmplyeebyId_WhenExists_ShouldReturnEmployee() {
        // Given
        when(emplyeeRepository.findById(1L)).thenReturn(Optional.of(testEmplyee));

        // When
        Optional<Emplyee> result = emplyeeService.getEmplyeebyId(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmplyeeID()).isEqualTo(1L);
        assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get().getFirstName()).isEqualTo("John");

        verify(emplyeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmplyeebyId_WhenNotExists_ShouldReturnEmpty() {
        // Given
        when(emplyeeRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Emplyee> result = emplyeeService.getEmplyeebyId(999L);

        // Then
        assertThat(result).isEmpty();
        verify(emplyeeRepository, times(1)).findById(999L);
    }

    @Test
    void getAllEmplyeeList_ShouldReturnAllEmployees() {
        // Given
        Emplyee employee2 = new Emplyee();
        employee2.setEmplyeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setFirstName("Jane");

        List<Emplyee> employees = Arrays.asList(testEmplyee, employee2);
        when(emplyeeRepository.findAll()).thenReturn(employees);

        // When
        List<Emplyee> result = emplyeeService.getAllEmplyeeList();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get(1).getEmail()).isEqualTo("jane.smith@example.com");

        verify(emplyeeRepository, times(1)).findAll();
    }

    @Test
    void getAllEmplyeeList_WhenEmpty_ShouldReturnEmptyList() {
        // Given
        when(emplyeeRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Emplyee> result = emplyeeService.getAllEmplyeeList();

        // Then
        assertThat(result).isEmpty();
        verify(emplyeeRepository, times(1)).findAll();
    }

    @Test
    void deleteEmpyeeById_ShouldCallRepositoryDelete() {
        // Given
        Long employeeId = 1L;
        doNothing().when(emplyeeRepository).deleteById(employeeId);

        // When
        emplyeeService.deleteEmpyeeById(employeeId);

        // Then
        verify(emplyeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void addEmplyee_ShouldSaveAndReturnEmployee() {
        // Given
        when(emplyeeRepository.save(any(Emplyee.class))).thenReturn(testEmplyee);

        // When
        Emplyee result = emplyeeService.addEmplyee(testEmplyee);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmplyeeID()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getFirstName()).isEqualTo("John");

        verify(emplyeeRepository, times(1)).save(any(Emplyee.class));
    }

    @Test
    void updateEmplyee_WhenExists_ShouldUpdateAndReturnEmployee() {
        // Given
        Emplyee updatedDetails = new Emplyee();
        updatedDetails.setEmail("john.updated@example.com");
        updatedDetails.setPassword("newpassword");
        updatedDetails.setFirstName("John");
        updatedDetails.setLastName("Updated");
        updatedDetails.setDeparment(testDepartment);

        when(emplyeeRepository.findById(1L)).thenReturn(Optional.of(testEmplyee));
        when(emplyeeRepository.save(any(Emplyee.class))).thenReturn(testEmplyee);

        // When
        Emplyee result = emplyeeService.updateEmplyee(1L, updatedDetails);

        // Then
        assertThat(result).isNotNull();
        verify(emplyeeRepository, times(1)).findById(1L);
        verify(emplyeeRepository, times(1)).save(any(Emplyee.class));
    }

    @Test
    void updateEmplyee_WhenNotExists_ShouldThrowException() {
        // Given
        when(emplyeeRepository.findById(999L)).thenReturn(Optional.empty());

        Emplyee updatedDetails = new Emplyee();
        updatedDetails.setEmail("john.updated@example.com");

        // When & Then
        assertThatThrownBy(() -> emplyeeService.updateEmplyee(999L, updatedDetails))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Emplyee not found with id: 999");

        verify(emplyeeRepository, times(1)).findById(999L);
        verify(emplyeeRepository, never()).save(any(Emplyee.class));
    }

    @Test
    void getEmplyeeListByDepartmentId_ShouldReturnEmployeesInDepartment() {
        // Given
        Emplyee employee2 = new Emplyee();
        employee2.setEmplyeeID(2L);
        employee2.setEmail("jane.smith@example.com");
        employee2.setDeparment(testDepartment);

        List<Emplyee> departmentEmployees = Arrays.asList(testEmplyee, employee2);
        when(emplyeeRepository.findByDeparmentDeparmentID(1L)).thenReturn(departmentEmployees);

        // When
        List<Emplyee> result = emplyeeService.getEmplyeeListByDepartmentId(1L);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDeparment().getDeparmentID()).isEqualTo(1L);
        assertThat(result.get(1).getDeparment().getDeparmentID()).isEqualTo(1L);

        verify(emplyeeRepository, times(1)).findByDeparmentDeparmentID(1L);
    }

    @Test
    void getEmplyeeListByDepartmentId_WhenNoEmployees_ShouldReturnEmptyList() {
        // Given
        when(emplyeeRepository.findByDeparmentDeparmentID(999L)).thenReturn(Arrays.asList());

        // When
        List<Emplyee> result = emplyeeService.getEmplyeeListByDepartmentId(999L);

        // Then
        assertThat(result).isEmpty();
        verify(emplyeeRepository, times(1)).findByDeparmentDeparmentID(999L);
    }

    @Test
    void addEmplyee_WithNullEmail_ShouldStillSave() {
        // Given
        Emplyee employeeWithNullEmail = new Emplyee();
        employeeWithNullEmail.setFirstName("Test");
        employeeWithNullEmail.setLastName("User");

        when(emplyeeRepository.save(any(Emplyee.class))).thenReturn(employeeWithNullEmail);

        // When
        Emplyee result = emplyeeService.addEmplyee(employeeWithNullEmail);

        // Then
        assertThat(result).isNotNull();
        verify(emplyeeRepository, times(1)).save(any(Emplyee.class));
    }

    @Test
    void updateEmplyee_ShouldUpdateAllFields() {
        // Given
        Deparment newDepartment = new Deparment();
        newDepartment.setDeparmentID(2L);
        newDepartment.setDepartmentName("HR Department");

        Emplyee updatedDetails = new Emplyee();
        updatedDetails.setEmail("newemail@example.com");
        updatedDetails.setPassword("newpass123");
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Smith");
        updatedDetails.setDeparment(newDepartment);

        when(emplyeeRepository.findById(1L)).thenReturn(Optional.of(testEmplyee));
        when(emplyeeRepository.save(any(Emplyee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Emplyee result = emplyeeService.updateEmplyee(1L, updatedDetails);

        // Then
        assertThat(result.getEmail()).isEqualTo("newemail@example.com");
        assertThat(result.getPassword()).isEqualTo("newpass123");
        assertThat(result.getFirstName()).isEqualTo("Jane");
        assertThat(result.getLastName()).isEqualTo("Smith");
        assertThat(result.getDeparment().getDeparmentID()).isEqualTo(2L);

        verify(emplyeeRepository, times(1)).findById(1L);
        verify(emplyeeRepository, times(1)).save(testEmplyee);
    }
}
