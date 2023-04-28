package fr.spear.employes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.spear.employes.bean.Employes;
import fr.spear.employes.repository.EmployesRepository;

@Service
public class EmployesService {

	@Autowired
	private EmployesRepository employesRepository;
	
	//Ajout d'un employe
	public void ajoutEmployes(Employes employes) {
		employesRepository.save(employes);
	}
	
	//Listing des employes
	public List <Employes> listeEmployes(){
		return employesRepository.findAll();
	}
	
	
	//SHow 
	public Optional<Employes> getEmployeIfExist(int id) {
		return employesRepository.findById(id);
		
	}
}
