package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
}
