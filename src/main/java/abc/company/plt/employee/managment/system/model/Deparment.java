package abc.company.plt.employee.managment.system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deparment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deparmentID;

    @Column(unique=true,nullable= false)
    private String departmentName;

    // One Department can have many Employees
    @OneToMany(mappedBy = "deparment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("department-employees")
    private Set<Emplyee> employees = new HashSet<>();

    // One Department can have many Projects
    @OneToMany(mappedBy = "deparment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("department-projects")
    private Set<Project> projects = new HashSet<>();
}
