package abc.company.plt.employee.managment.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"deparment", "employees"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectID;

    @Column(unique = true, nullable = false)
    private String projectName;

    // Many Projects can belong to one Department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deparment_Id")
    @JsonIgnore
    private Deparment deparment;

    // Many-to-Many: Project can have many Employees
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonIgnore
    private Set<Emplyee> employees = new HashSet<>();
}