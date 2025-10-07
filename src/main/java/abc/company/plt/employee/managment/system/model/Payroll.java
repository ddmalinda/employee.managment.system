package abc.company.plt.employee.managment.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollID;

    @Column( nullable = false)
    private Double basicSalary;
    private Double bouns;
    private Double deductions;

    @Column( nullable = false)
    private Double netSalary;
}
