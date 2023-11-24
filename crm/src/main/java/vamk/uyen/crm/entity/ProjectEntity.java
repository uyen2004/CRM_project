package vamk.uyen.crm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class ProjectEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

//    @OneToMany(mappedBy = "project")
//    private Set<Task> tasks;
}
