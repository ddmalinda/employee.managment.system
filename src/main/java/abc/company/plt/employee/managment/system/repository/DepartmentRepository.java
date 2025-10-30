package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
