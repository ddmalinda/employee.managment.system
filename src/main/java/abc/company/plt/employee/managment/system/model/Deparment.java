package abc.company.plt.employee.managment.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Deparment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deparmentID;

    @Column(unique=true,nullable= false)
    private String departmentName;
}
