package pet.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.dao.CustomerDao;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private PetStoreService petStoreService;
	
	/* +========	implementing saveCustomer() ==========+	*/

	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		
		PetStore petStore = petStoreService.findPetStoreById(petStoreId);
		
		Customer customer = findOrCreateCustomerById(petStoreCustomer.getCustomerId(), petStoreId);
		
		setCustomerFields(customer, petStoreCustomer);
		
		customer.getPetStores().add(petStore);	// I need to further understand Michael code
		
		petStore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new PetStoreCustomer(dbCustomer);
		
	}	
	
	/* +========	implementing setCustomerFields() ==========+	*/

	private void setCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {

		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}
	
	/* +========	implementing findOrCreateCustomer() ==========+	*/

	private Customer findOrCreateCustomerById(Long customerId, Long petStoreId) {
		
		Customer customer;
		
		if (Objects.isNull(customerId)) {
			
			customer = new Customer();
		}
		
		else {
			
			customer = findCustomerById(customerId, petStoreId);
		}

		return customer;
	}
	
	/* +========	implementing findCustomerById() ==========+	*/

	private Customer findCustomerById(Long customerId, Long petStoreId) {

		/*
		return  customerDao.findById(customerId).orElseThrow(
				() -> new NoSuchElementException(
						"Customer with ID = " + customerId + " does not exist."
						)
				);	*/
		
		Customer customer = customerDao.findById(customerId).orElseThrow(
				() -> new NoSuchElementException(
						"Customer with ID = " + customerId + " does not exist."
						)
				);
		
		boolean found = false;
		
		for (PetStore petStore : customer.getPetStores()) {
			
			if (petStore.getPetStoreId() == petStoreId) {
				
				found = true;
				break;
			}
		}
		
		if (!found) {
			
			throw new IllegalArgumentException("customer with id = " + customerId + " is not a member of "
					+ "petStore with Id = " + petStoreId);
		}
		
		return customer;
	}
	
	/* +========	implementing deleteCustomerById() ==========+	*/

	@Transactional(readOnly = false)
	public void deleteCustomerById(Long petStoreId, Long customerId) {

		PetStore petStore = petStoreService.findPetStoreById(petStoreId);
		
		Customer customer = findCustomerById(customerId, petStoreId);
		/*
		if (!customer.getPetStores().contains(petStore)) // Not sure about this.
		
		{
			// lost here. I need to review this code with Michael.
			
			throw new IllegalStateException(
					"Customer with ID = " + customerId + " was not owned by pet Store with ID = " + petStoreId);
			
		}	*/
		
		petStore.getCustomers().remove(customer);	// best method from Michael.
		customerDao.delete(customer);
		
		//customerDao.deleteById(customerId);
	}
	
	/* +========	implementing retrieveCustomerById() ==========+	*/

	@Transactional(readOnly = true)
	public PetStoreCustomer retrieveCustomerById(Long petStoreId, Long customerId) {

		PetStore petStore = petStoreService.findPetStoreById(petStoreId);
		
		Customer customer = findCustomerById(customerId, petStoreId);
		
		/*
		if (!customer.getPetStores().contains(petStore)) // Not sure about this.
		
		{
			// lost here. I need to review this code with Michael.
			
			throw new IllegalStateException(
					"Customer with ID = " + customerId + " was not owned by pet Store with ID = " + petStoreId);
			
		}	*/
		return new PetStoreCustomer(customer);
	}
	
	/* +========	implementing retrieveAllCustomersInPetStoreId() ==========+	*/

	public List<PetStoreCustomer> retrieveAllCustomersInPetStoreId(Long petStoreId) {

		PetStore petStore = petStoreService.findPetStoreById(petStoreId);
		
		Set<Customer> customers = petStore.getCustomers();
		
		List<PetStoreCustomer> petStoreCustomers = new ArrayList<PetStoreCustomer>();
		
		for (Customer customer : customers) {
			
			PetStoreCustomer petStoreCustomer = new PetStoreCustomer(customer);
			
			petStoreCustomers.add(petStoreCustomer);
		}
		
		return petStoreCustomers;
	}
	
	/* +========	implementing retrieveAllCustomers() ==========+	*/

	public List<PetStoreCustomer> retrieveAllCustomers() {

		List<Customer> customers = customerDao.findAll();
		
		List<PetStoreCustomer> petStoreCustomers = new ArrayList<PetStoreCustomer>();
		
		for (Customer customer : customers) {
			
			PetStoreCustomer petStoreCustomer = new PetStoreCustomer(customer);
			
			petStoreCustomers.add(petStoreCustomer);
		}
		return petStoreCustomers;
	}
	
}
