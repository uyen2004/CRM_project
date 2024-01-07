package vamk.uyen.crm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

//	@ManyToOne
//    @JoinColumn(name="user_id", nullable=false)
//    private User user;
//
//	@ManyToOne
//    @JoinColumn(name="project_id", nullable=false)
//    private ProjectEntity projectEntity;
//
//	@ManyToOne
//    @JoinColumn(name="status_id", nullable=false)
//    private Status status;


}
