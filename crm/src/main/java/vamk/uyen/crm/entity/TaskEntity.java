package vamk.uyen.crm.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class TaskEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "start_date", nullable = false)
	private String startDate;

	@Column(name = "end_date", nullable = false)
	private String endDate;


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
