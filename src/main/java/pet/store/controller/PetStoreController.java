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
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	
	@Autowired
	private PetStoreService petStoreService;

/* +=================	CREATE = POST	===========================+ */
	
	@PostMapping("/petStoreData")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		
		log.info("Creating Pet Store Data {}", petStoreData);
		
		return petStoreService.savePetStore(petStoreData);
	}
	
/* +=================	UPDATE = PUT	===========================+ */
	
	@PutMapping("/petStore/{petStoreDataId}")
	public PetStoreData updatePetStore (
			@PathVariable Long petStoreDataId,
			@RequestBody PetStoreData petStoreData) {
		
		petStoreData.setPetStoreId(petStoreDataId);
		log.info("Updating Pet Store Data {}", petStoreData);
		
		return petStoreService.savePetStore(petStoreData);
	}
	
	/* +=================	READ = GET	===========================+ */
	
	@GetMapping
	public List<PetStoreData> retrieveAllPetStores () {
		
		log.info("Retrieving all Pet Stores with employees and customers.");
		
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/onlyPetStores")
	public List<PetStoreData> retrieveAllPetStoresWithNoEmployeeAndNoCustomer () {
		
		log.info("Retrieving all Pet Stores with no employee and no customer.");
		
		return petStoreService.retrieveAllPetStoresWithNoEmployeeAndNoCustomer();
	}
 	
	@GetMapping("/{petStoreId}")
	public PetStoreData retrievePetStoreById (@PathVariable Long petStoreId) {
		
		log.info("Retrieving a Pet Store with ID = {}", petStoreId);
		
		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
	/* +=================	DELETE = DELETE	===========================+ */
	
	@DeleteMapping("/petStore")
	public void deleteAllPetStores () {
		
		log.info("Attempting to delete all Pet Stores.");
		
		throw new UnsupportedOperationException(
				"Deleting all pet stores is not allowed."
				);
	}
	
	
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreById (@PathVariable Long petStoreId) {
		
		log.info("Deleting a Pet Store with ID = {}", petStoreId);
		
		petStoreService.deletePetStoreById(petStoreId);
		
		return Map.of(
				"message", "Deletion of pet store with ID = " + petStoreId + " was successful."
				);
	}

}
