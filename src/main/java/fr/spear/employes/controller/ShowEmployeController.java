package fr.spear.employes.controller;

import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fr.spear.employes.bean.Employes;
import fr.spear.employes.service.EmployesService;

@Controller
public class ShowEmployeController {
	
	@Autowired
	private EmployesService empService;

	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Long id, Model model, Employes employes) {
		Optional<Employes> getEmployeFounded = empService.getEmployeIfExist(id);
	        
        if(getEmployeFounded.isPresent()) {
        	
        	model.addAttribute("employes",getEmployeFounded.get());
        }
		return "showEmploye";
	}
	
	@PostMapping("/show/{id}")
    public String update(@PathVariable(value = "id") Long id, @Validated Employes employeDetails, BindingResult bindingResult) throws AttributeNotFoundException {
		/*
        * La ligne ci-dessous joue un role essentiel. 
        * Il check si l'id, il le reoturne dans la variable sinon il léve une exception
        */
		Employes employe = empService.getEmployeIfExist(id)
       .orElseThrow(() -> new AttributeNotFoundException("Aucun friend avec l'id :: " + id));

		employe.setNom(employeDetails.getNom());
		employe.setPrenom(employeDetails.getPrenom());
		employe.setEmail(employeDetails.getEmail());
        
		empService.ajoutEmployes(employe, bindingResult);
        
        return "redirect:/";
    }

	
}
