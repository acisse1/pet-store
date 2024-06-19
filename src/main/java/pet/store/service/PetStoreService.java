package pet.store.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	
	@Autowired
	private PetStoreDao petStoreDao;

	/* +========	implementing savePetStore() ==========+	*/
	
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		
		Long petStoreId = petStoreData.getPetStoreId();
		
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		setFieldsInPetStore(petStore, petStoreData);
		
		return new PetStoreData(petStoreDao.save(petStore));
		
	}

	private void setFieldsInPetStore(PetStore petStore, PetStoreData petStoreData) {
		
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		
	}

	/* +========	implementing findOrCreatePetStore() ==========+	*/
	
	private PetStore findOrCreatePetStore(Long petStoreId) {
		
		PetStore petStore;
		
		if (Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		}
		
		else {
			petStore = findPetStoreById(petStoreId);
		}
		
		return petStore;
		
	}
	
	/* +========	implementing findPetStoreById() ==========+	*/
	/* +========	I made it public for package visibility ==========+	*/
	
	public PetStore findPetStoreById(Long petStoreId) {
		
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID = " + petStoreId + " was not found."
						));
	}
	
	/* +========	implementing retrieveAllPetStores() ==========+	*/
	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		/*
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> response = new LinkedList<>();
		
		for (PetStore petStore : petStores) {
			
			response.add(new PetStoreData(petStore));
		}
		
		return response; */
		
		/*  +=========	Alternative return to get all pet stores	======+ */
		
		// @formatter: off 
		return petStoreDao.findAll()
					.stream()
					.map(pet_Store -> new PetStoreData(pet_Store))
					.toList();
		// @formatter: on
	}

	/* +========	implementing retrievePetStoreById() ==========+	*/
	
	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petStoreId) {

		PetStore petStore = findPetStoreById(petStoreId);
		
		return new PetStoreData(petStore);
	}

	
	/* +========	implementing deletePetStoreById() ==========+	*/
	
	@Transactional(readOnly = false)
	public void deletePetStoreById(Long petStoreId) {
		
		PetStore petStore = findPetStoreById(petStoreId);
		
		petStoreDao.delete(petStore);
		
	}

	/* +==== implementing retrieveAllPetStoresWithNoEmployeeAndNoCustomer() ===+ */
	
	public List<PetStoreData> retrieveAllPetStoresWithNoEmployeeAndNoCustomer() {

		List<PetStore> petStores = petStoreDao.findAll();
		
		List<PetStoreData> petStoreDatas = new ArrayList<PetStoreData>();
		
		for (PetStore petStore : petStores) {
			
			PetStoreData petStoreData = new PetStoreData(petStore);
			
			petStoreData.getEmployees().clear();
			petStoreData.getCustomers().clear();
			
			petStoreDatas.add(petStoreData);
		}
		
		return petStoreDatas;
	}

}
