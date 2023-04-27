package fr.spear.employes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fr.spear.employes.bean.Employes;
import fr.spear.employes.repository.EmployesRepository;
import fr.spear.employes.service.EmployesService;

@Controller
public class EmployesController {

	@Autowired
	private EmployesService employesService;
	
	@Autowired
	private EmployesRepository employesRepository;
	
	@GetMapping("/")
	public String getList(Model model, Employes employes) {
		getEmployeesList(model);
		return "employes";
	}
	
	@PostMapping("/")
	public String employes(@Validated Employes employes, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			getEmployeesList(model);
			return "employes";
		}else {
			employesService.ajoutEmployes(employes);
			return "redirect:/";
		}
	}
	
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") int id, Model model, Employes employes) {
		
		Optional<Employes> getEmployes = employesRepository.findById(id);
                
        Employes ami = null;
        
        if(getEmployes.isPresent()) {
        	ami= getEmployes.get();
        
        	model.addAttribute("amis",ami);
        }
		return "employes";
	}
	
	
	
	public void getEmployeesList(Model model) {
        if (employesService.listeEmployes().size() > 0) {
            model.addAttribute("employe", employesService.listeEmployes());
        }
    }
}
	