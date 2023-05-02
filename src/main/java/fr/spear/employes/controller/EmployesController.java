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
import org.springframework.web.bind.annotation.RequestParam;

import fr.spear.employes.bean.Employes;
import fr.spear.employes.service.EmployesService;

@Controller
public class EmployesController {

	@Autowired
	private EmployesService employesService;
	
	@GetMapping("/")
	public String getList(Employes eee,  @RequestParam(value = "id", required = false) Long id,Model model) {
//		public String getList(Model model, Employes employes) {
		
		if(id != null) {
			
			Optional<Employes> employe = employesService.getEmployeIfExist(id);
			if(employe.isPresent()) {
				model.addAttribute("employes", employe.get());
			}
		}
		getEmployeesList(model);
		return "employes";
	}
	
	@PostMapping("/")
	public String employes(@Validated Employes employes, BindingResult bindingResult, Model model, @RequestParam("id") Optional<Long> id) {
		if(bindingResult.hasErrors()) {
			
			getEmployeesList(model);
			return "employes";
		}else if(employesService.ajoutEmployes(employes, bindingResult, id) != null) {
			
			
	    	getEmployeesList(model);
	    	return "employes";
		}else {
			employesService.ajoutEmployes(employes, bindingResult, id);
			model.addAttribute("msg",employes);

			return "employes";
		}
	}
	
	public void getEmployeesList(Model model) {
        if (employesService.listeEmployes().size() > 0) {
            model.addAttribute("employe", employesService.listeEmployes());
        }
    }
	
	//DELETE 
	//*************DELETE
		@GetMapping("/delete/{id}")
		public String supprimer(@PathVariable(value="id") int employeId, Model model) {
			
			if (employesService.deleteEmploye(employeId)) {
				model.addAttribute("msg","success");
				
			} else {
				model.addAttribute("msg","echec");
			}
			return "redirect:/";
		}
}
	