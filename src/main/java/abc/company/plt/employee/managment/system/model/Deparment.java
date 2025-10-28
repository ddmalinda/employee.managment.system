package abc.company.plt.employee.managment.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "deparment_id")
    @JsonManagedReference
    private List<Emplyee> emplyee;
}
