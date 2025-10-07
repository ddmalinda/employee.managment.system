package abc.company.plt.employee.managment.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@AllArgsConstructor
public class Emplyee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emplyeeID;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
}
