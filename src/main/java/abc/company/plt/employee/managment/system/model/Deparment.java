package abc.company.plt.employee.managment.system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@ToString(exclude = {"employees", "projects"})
public class Deparment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deparmentID;

    @Column(unique=true, nullable=false)
    private String departmentName;

    @OneToMany(mappedBy = "deparment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("department-employees")
    private Set<Emplyee> employees = new HashSet<>();

    @OneToMany(mappedBy = "deparment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("department-projects")
    private Set<Project> projects = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deparment)) return false;
        Deparment that = (Deparment) o;
        return deparmentID != null && deparmentID.equals(that.deparmentID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
