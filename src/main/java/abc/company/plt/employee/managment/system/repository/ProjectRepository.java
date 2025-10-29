package abc.company.plt.employee.managment.system.repository;

import abc.company.plt.employee.managment.system.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
