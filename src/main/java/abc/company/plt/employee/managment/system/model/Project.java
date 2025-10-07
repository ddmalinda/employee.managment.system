package abc.company.plt.employee.managment.system.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prijectId;

    @Column(unique= true,nullable = false)
    private String projectName;

    private Date startdate;
    private Date endDate;

}
