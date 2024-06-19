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
import pet.store.controller.model.PetStoreCustomer;
import pet.store.service.CustomerService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	/* +=================	CREATE = POST	===========================+ */
	
	
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertCustomer (
			@PathVariable long petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer) {
		
		log.info("Creating Customer {} for Pet Store with ID = {}", petStoreCustomer, petStoreId);
		
		return customerService.saveCustomer(petStoreId, petStoreCustomer);
		
	}
	
	
	/* +=================	UPDATE = PUT	===========================+ */
	
	@PutMapping("/{petStoreId}/customer/{customerId}")
	public PetStoreCustomer updateCustomer (
			@PathVariable Long petStoreId,
			@PathVariable Long customerId,
			@RequestBody PetStoreCustomer petStoreCustomer) {
		
		petStoreCustomer.setCustomerId(customerId);
		
		log.info("Updating Customer {} with ID = {} for Pet Store with ID = {}", 
				petStoreCustomer, customerId, petStoreId);
		
		return customerService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	
	/* +=================	READ = GET	===========================+ */
	
	@GetMapping("/{petStoreId}/customer/{customerId}")
	public PetStoreCustomer retrieveCustomerById (
			@PathVariable Long petStoreId,
			@PathVariable Long customerId) {
		
		log.info("Retrieving Customer with ID = {} for pet Store with ID = {}.", 
				customerId, petStoreId);
		
		return customerService.retrieveCustomerById(petStoreId, customerId);
	}
	
	@GetMapping("/{petStoreId}/customer")
	public List<PetStoreCustomer> retrieveAllCustomersInPetStoreId (
			@PathVariable Long petStoreId) {
		
		log.info("Retrieving all customers in the pet Store with ID = {}.", petStoreId);
		
		
		return customerService.retrieveAllCustomersInPetStoreId(petStoreId);
	}
	
	
	@GetMapping("/customer")
	public List<PetStoreCustomer> retrieveAllCustomers () {
		
		log.info("Retrieving all customers.");
		
		return customerService.retrieveAllCustomers();
	}
	
	
	/* +=================	DELETE = DELETE	===========================+ */
	
	@DeleteMapping("/{petStoreId}/customer/{customerId}")
	public Map<String, String> deleteCustomerById (
			@PathVariable Long petStoreId,
			@PathVariable Long customerId) {
		
		log.info("Deleling Customer with ID = {} for pet Store with ID = {}.",
				customerId, petStoreId);
		
		customerService.deleteCustomerById(petStoreId, customerId);
		
		return Map.of(
				"message", "Deletion of customer with ID = " + customerId +
				" for the pet store with ID = " + petStoreId + " was successful.");
	}
	
}
