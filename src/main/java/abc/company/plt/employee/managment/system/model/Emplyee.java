package abc.company.plt.employee.managment.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"deparment", "projects"})
@Table(name = "emplyee")
public class Emplyee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emplyeeID;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deparment_Id")
    @JsonBackReference("department-employees")
    private Deparment deparment;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emplyee)) return false;
        Emplyee emplyee = (Emplyee) o;
        return emplyeeID != null && emplyeeID.equals(emplyee.emplyeeID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
