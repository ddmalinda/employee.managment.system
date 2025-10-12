package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll,Long> {
}
