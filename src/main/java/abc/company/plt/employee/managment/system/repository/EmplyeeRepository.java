package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.Emplyee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmplyeeRepository  extends JpaRepository<Emplyee, Long> {
    // Use built-in JpaRepository methods like findById, findAll, save, deleteById
    List<Emplyee> findbyDepartmentId(Long departmentID);
}
