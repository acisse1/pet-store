package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.entity.Employee;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.EmployeeService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
/* +=================	CREATE = POST	===========================+ */
	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee(
			@PathVariable Long petStoreId,
			@RequestBody PetStoreEmployee petStoreEmployee) {
		
		log.info("Creating Employee {} for Pet Store with ID = {}", petStoreEmployee, petStoreId);
		
		return employeeService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
/* +=================	UPDATE = PUT	===========================+ */
	
	@PutMapping("/{petStoreId}/employee/{employeeId}")
	public PetStoreEmployee updateEmployee(
			@PathVariable Long petStoreId,
			@PathVariable Long employeeId,
			@RequestBody PetStoreEmployee petStoreEmployee) {
		
		petStoreEmployee.setEmployeeId(employeeId);
		
		log.info("Updating Employee {} with ID = {} for Pet Store with ID = {}", 
				petStoreEmployee, employeeId, petStoreId);
		
		return employeeService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	/* +=================	READ = GET	===========================+ */
	
	@GetMapping("/{petStoreId}/employee/{employeeId}")
	public PetStoreEmployee retrieveEmployeeById (
			@PathVariable Long petStoreId,
			@PathVariable Long employeeId) {
		
		log.info("Retrieving Employee with ID = {} for pet Store with ID = {}.", 
				employeeId, petStoreId);
		
		return employeeService.retrieveEmployeeById(petStoreId, employeeId);
	}
	
	
	@GetMapping("/{petStoreId}/employee")
	public List<PetStoreEmployee> retrieveAllEmployeesInPetStoreId (
			@PathVariable Long petStoreId) {
		
		log.info("Retrieving all employees in the pet Store with ID = {}.", petStoreId);
		
		return employeeService.retrieveAllEmployeesInPetStoreId(petStoreId);
	}
	
	
	@GetMapping("/employee")
	public List<PetStoreEmployee> retrieveAllEmployees () {
		
		log.info("Retrieving all employees.");
		
		return employeeService.retrieveAllEmployees();
	}
	
	
	/* +=================	DELETE = DELETE	===========================+ */
	
	@DeleteMapping("/{petStoreId}/employee/{employeeId}")
	public Map<String, String> deleteEmployeeById(
			@PathVariable Long petStoreId,
			@PathVariable Long employeeId) {
		
		log.info("Deleling Employee with ID = {} for pet Store with ID = {}.",
				employeeId, petStoreId);
		
		employeeService.deleteEmployeeById(petStoreId, employeeId);
		
		return Map.of(
				"message", "Deletion of employee with ID = " + employeeId +
				" for the pet store with ID = " + petStoreId + " was successful.");
		
	}
	

}
