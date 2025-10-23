package abc.company.plt.employee.managment.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    //Many Emplyees can belong to one Business
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deparmentID")
    @JsonBackReference
    private Deparment deparment;
}
