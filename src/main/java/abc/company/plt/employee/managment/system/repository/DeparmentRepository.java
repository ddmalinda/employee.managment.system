package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.Deparment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeparmentRepository extends JpaRepository<Deparment,Long> {
}
