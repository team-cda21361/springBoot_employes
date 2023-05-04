package fr.spear.employes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.spear.employes.bean.Article;
import fr.spear.employes.bean.User;
import fr.spear.employes.bean.UserLogin;
import fr.spear.employes.service.ArticleService;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/ajout-article")
	public String ajout(Article article) {
		
		return "article/ajout";
	}
	@PostMapping("/ajout-article")
	public String ajout(@Validated Article article, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "article/form";
		}
		
		//On recupere le nom de l'utilisateur connecté
				String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
					System.err.println(username);
				
				
				return "article/ajout";
		
	}
}
