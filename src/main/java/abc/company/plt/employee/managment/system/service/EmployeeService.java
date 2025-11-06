package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.model.Employee;
import abc.company.plt.employee.managment.system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Find one employee by ID
    public Optional<Employee> getEmployeeById(Long employeeID){
        if(employeeID==null || employeeID<=0){
            throw new IllegalArgumentException("Invalid Employee ID");
        }
        return employeeRepository.findById(employeeID);
    }

    // Find all employees
    public List<Employee> getAllEmployeeList(){
        return employeeRepository.findAll();
    }

    // Delete an employee by ID
    public void deleteEmployeeById(Long id){
        if(id==null || id<=0){
            throw new IllegalArgumentException("Invalid Employee ID");
        }
        employeeRepository.deleteById(id);
    }

    // Add a new employee
    public Employee addEmployee(Employee employee){
        if (employee == null || employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Employee email cannot be null or empty");
        }
        return employeeRepository.save(employee);
    }

    // Update an existing employee
    public Employee updateEmployee(Long employeeId, Employee employeeDetails){
        if(employeeId==null || employeeId<=0){
            throw new IllegalArgumentException("Invalid Employee ID");
        }
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        // Update fields from the details provided
        employee.setEmail(employeeDetails.getEmail());
        employee.setPassword(employeeDetails.getPassword()); 
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setDepartment(employeeDetails.getDepartment());
        return employeeRepository.save(employee);
    }

    // Find employees belonging to a specific department
    public List<Employee> getEmployeeListByDepartmentId(Long departmentId){
        return employeeRepository.findByDepartmentDepartmentID(departmentId);
    }
}