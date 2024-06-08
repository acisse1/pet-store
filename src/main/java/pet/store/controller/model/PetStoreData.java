package pet.store.controller.model;

import java.util.HashSet;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {
	
	private Long petStoreId;
	
	private String petStoreName;
	
	private String petStoreAddress; 
	
	private String petStoreCity;
	
	private String petStoreState;
	
	private String petStoreZip;
	
	private String petStorePhone;
	
	private Set<PetStoreEmployee> employees = new HashSet<PetStoreEmployee>();
	
	private Set<PetStoreCustomer> customers = new HashSet<PetStoreCustomer>();

	// constructor
	public PetStoreData(PetStore petStore) {
		
		this.petStoreId = petStore.getPetStoreId();
		this.petStoreName = petStore.getPetStoreName();
		this.petStoreAddress = petStore.getPetStoreAddress();
		this.petStoreCity = petStore.getPetStoreCity();
		this.petStoreState = petStore.getPetStoreState();
		this.petStoreZip = petStore.getPetStoreZip();
		this.petStorePhone = petStore.getPetStorePhone();
		
		
		for (Employee employee : petStore.getEmployees()) {
			
			PetStoreEmployee petStoreEmployee = new PetStoreEmployee(employee);
			this.employees.add(petStoreEmployee);
		}
		
		
		for (Customer customer : petStore.getCustomers()) {
			
			PetStoreCustomer petStoreCustomer = new PetStoreCustomer(customer);
			this.customers.add(petStoreCustomer);
		}
	}
	

}
