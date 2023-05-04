package fr.spear.employes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.spear.employes.bean.User;
import fr.spear.employes.repository.RoleRepo;
import fr.spear.employes.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService  userService;

	@Autowired
	private RoleRepo repo;
	
	@GetMapping("/register")
	public String register(User user, Model model) {
		
		model.addAttribute("roles", repo.findAll());
		
		return "user/register";
	}
	
	@PostMapping("/register")
	public String register(@Validated User user, BindingResult bindingResult) {
		userService.ajoutUser(user);
		return "user/register";
	}
}
