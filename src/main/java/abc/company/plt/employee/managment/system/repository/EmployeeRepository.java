package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository  extends JpaRepository<employee, Long> {
    // Use built-in JpaRepository methods like findById, findAll, save, deleteById
    List<employee> findByDepartmentDepartmentID(Long id);
    Optional<employee> findById(Long employeeId);
}
