package pet.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.EmployeeDao;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private PetStoreService petStoreService;

	/* +========	implementing saveEmployee() ==========+	*/
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {

		PetStore petStore = petStoreService.findPetStoreById(petStoreId);
		
		Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId());
		
		setEmployeeFields(employee, petStoreEmployee);
		
		employee.setPetStore(petStore);
		
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(dbEmployee);
	}
	
	/* +========	implementing setEmployeeFields() ==========+	*/

	private void setEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {

		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}

	/* +========	implementing findOrCreateEmployee() ==========+	*/

	private Employee findOrCreateEmployee(Long employeeId) {

		Employee employee;
		
		if (Objects.isNull(employeeId)) {
			
			employee = new Employee();
		}
		else {
			
			employee = findEmployeeById(employeeId);
		}
		
		return employee;
	}
	
	/* +========	implementing findEmployeeById() ==========+	*/

	private Employee findEmployeeById(Long employeeId) {

		return employeeDao.findById(employeeId)
				.orElseThrow(
						() -> new NoSuchElementException(
								"Employee with ID = " + employeeId + " does not exist.")
						);
	}
	
	/* +========	implementing retrieveEmployeeById() ==========+	*/

	@Transactional(readOnly = true)
	public PetStoreEmployee retrieveEmployeeById(Long petStoreId, Long employeeId) {

		petStoreService.findPetStoreById(petStoreId);
		
		Employee employee = findEmployeeById(employeeId);
		
		if (employee.getPetStore().getPetStoreId() != petStoreId) {
			
			throw new IllegalStateException("Employee with ID = " + employeeId +
					" was not owned by pet Store with ID = " + petStoreId);
		}
		
		return new PetStoreEmployee(employee);
	}
	
	/* +========	implementing deleteEmployeeById() ==========+	*/

	@Transactional(readOnly = false)
	public void deleteEmployeeById(Long petStoreId, Long employeeId) {

		petStoreService.findPetStoreById(petStoreId);
		
		Employee employee = findEmployeeById(employeeId);
		
		if (employee.getPetStore().getPetStoreId() != petStoreId) {

			throw new IllegalStateException(
					"Employee with ID = " + employeeId + " was not owned by pet Store with ID = " + petStoreId);
		}
		
		employeeDao.delete(employee);
	}

	/* +========	implementing retrieveAllEmployeesInPetStoreId() ==========+	*/
	
	@Transactional(readOnly = true)
	public List<PetStoreEmployee> retrieveAllEmployeesInPetStoreId(Long petStoreId) {
		
		PetStore petStore = petStoreService.findPetStoreById(petStoreId);
		
		Set<Employee> employees = petStore.getEmployees();
		
		List<PetStoreEmployee> petStoreEmployees = new ArrayList<PetStoreEmployee>();
		
		for (Employee employee : employees) {
			
			PetStoreEmployee petStoreEmployee = new PetStoreEmployee(employee);
			
			petStoreEmployees.add(petStoreEmployee);
		}

		return petStoreEmployees;
	}
	
	/* +========	implementing retrieveAllEmployees() ==========+	*/

	public List<PetStoreEmployee> retrieveAllEmployees() {

		List<Employee> employees = employeeDao.findAll();
		
		List<PetStoreEmployee> petStoreEmployees = new ArrayList<PetStoreEmployee>();
		
		for (Employee employee : employees) {
			
			PetStoreEmployee petStoreEmployee = new PetStoreEmployee(employee);
			
			petStoreEmployees.add(petStoreEmployee);
		}
		
		return petStoreEmployees;
	}

}
