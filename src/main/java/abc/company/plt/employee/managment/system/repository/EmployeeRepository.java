package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    // Use built-in JpaRepository methods like findById, findAll, save, deleteById
    List<Employee> findByDepartmentDepartmentID(Long id);
}
