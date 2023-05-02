package fr.spear.employes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestParam;

import fr.spear.employes.bean.Employes;
import fr.spear.employes.repository.EmployesRepository;

@Service
public class EmployesService {

	@Autowired
	private EmployesRepository employesRepository;
	
	//Ajout d'un employe
	public Employes ajoutEmployes(Employes employes, BindingResult bindingResult, @RequestParam("id") Optional<Long> id) {
		//Check si le mail existe
		if( id.isPresent()){
			System.out.println("pOST --   " + id);
		
			employesRepository.save(employes);
			
		}else if(employesRepository.findByEmail(employes.getEmail()) != null) {
			employes  = employesRepository.findByEmail(employes.getEmail());
			
	    	bindingResult.addError(new FieldError("employes","email","Le mail ("+employes.getEmail()+") existe deja coco...."));
			
		}else {			
			 employesRepository.save(employes);
		}
		return employes;
	}
	
	//Listing des employes
	public List <Employes> listeEmployes(){
		return employesRepository.findAll();
	}
	
	
	//SHow 
	public Optional<Employes> getEmployeIfExist(Long id) {
		
		return employesRepository.findById(Integer.parseInt(String.valueOf(id)));
		
	}
	
	//DELETE
	public boolean deleteEmploye(int id) {
		Optional<Employes> employe =employesRepository.findById(id);
		
		if(employe.isPresent()) {
			
			employesRepository.delete(employe.get());
			System.out.println("deleted " );
			return true;
		}
		return false;
	}
}
