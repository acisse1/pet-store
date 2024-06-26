package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", nullable = false, unique = true)
	private Long employeeId;
	
	@Column(name = "employee_first_name")
	private String employeeFirstName;
	
	@Column(name = "employee_last_name")
	private String employeeLastName;
	
	@Column(name = "employee_phone")
	private String employeePhone;
	
	@Column(name = "employee_job_title")
	private String employeeJobTitle;
	
	@ManyToOne(cascade = CascadeType.PERSIST)	// "All" delete the pet store. "Persist" delete only employee
	@JoinColumn(name = "pet_store_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private PetStore petStore;

}
