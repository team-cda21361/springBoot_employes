package fr.spear.employes.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.spear.employes.bean.Article;
import fr.spear.employes.bean.Commentaire;
import fr.spear.employes.bean.User;
import fr.spear.employes.bean.UserLogin;
import fr.spear.employes.service.ArticleService;
import fr.spear.employes.service.CommentService;
import fr.spear.employes.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentServ;
	
	/*
	 * AJOUT ARTICLE
	 */
	@GetMapping("/ajout-article")
	public String ajout(Article article) {
		
		return "article/ajout";
	}
	@PostMapping("/ajout-article")
	public String ajout(@Validated Article article, BindingResult bindingResult, HttpSession session,
			@RequestParam("image") MultipartFile multipartFile) {
		
		if(bindingResult.hasErrors()) {
			System.err.println(bindingResult.getFieldErrorCount());
			System.err.println(bindingResult.getFieldError());
			return "article/ajout";
		}else {
			//On recupere le nom de l'utilisateur connecté
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
			
			if(username != null && !multipartFile.isEmpty()) {
				/*
				 * Upload image
				 */
				
				String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				
				article.setPhoto(filename);
				
				try {
					 
					//DESTINATION
					//String uploadDir = "static/images/" +article.getTitre();
					String destination = new ClassPathResource("/src/main/resources/static/upload").getPath();
					
					articleService.uploadsImage(destination, filename, multipartFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
				/*
				 * Upload image
				 */
				
				//Instnacie un nouvel objet avec le mail de user connecté
				User user = new User();
				user = userService.getUserByEmail(username);
				
				System.out.println(user);
				
				article.setUser(user);
				
				session.setAttribute("add","L'article "+article.getTitre()+" a bien ajouté");
				articleService.ajoutArticle(article);
				
			}
			return "article/list";
		}
	
	
	/*
	 * LISTING DES ARTICLES
	 */
	@GetMapping("/list")
	public String listing(Model model) {
		model.addAttribute("articles", articleService.getArticles());
		
		return "/article/list";
	}
	
	/*
	 * SHOW  ARTICLES
	 */
	@GetMapping("/showArticle/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		Optional<Article> article = articleService.getArticle(id);
	        
        if(article.isPresent()) {
        	
        	model.addAttribute("article",article.get());
        }
        
        List<Commentaire> comments = commentServ.findAll(id);

        model.addAttribute("comments", comments);
        
		return "/article/show";
	}
	
	@PostMapping("/showArticle/{id}")
    public String update(@PathVariable(value = "id") int id, @Validated Article articleDetails, BindingResult bindingResult, 
    		HttpSession session, @Validated Commentaire comment) throws AttributeNotFoundException {
		
		System.err.println(articleDetails.getTitre());
		System.err.println(articleDetails.getResume());
		System.err.println(articleDetails.getContenu());
		
		//On recupere le nom de l'utilisateur connecté
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		if(username != null && (comment.getContent() != null) ){
				//Instnacie un nouvel objet avec le mail de user connecté
				User user = new User();
				user = userService.getUserByEmail(username);
			comment.setUser(user);
			comment.setArticle(comment.getArticle());
			
			commentServ.createComment(comment);
		} else {
			/*
			 * La ligne ci-dessous joue un role essentiel. 
			 * Il check si l'id, il le reoturne dans la variable sinon il léve une exception
			 */
			Article article = articleService.getArticle(id)
					.orElseThrow(() -> new AttributeNotFoundException("Aucun article trouvé avec l'id :: " + id));
			
			article.setTitre(articleDetails.getTitre());
			article.setResume(articleDetails.getResume());
			article.setContenu(articleDetails.getContenu());
			/*
			 * TODO
			 * Rajouter le user
			 */
			
			session.setAttribute("update","L'article "+article.getTitre()+" a bien modifié");
			articleService.ajoutArticle(article);

		}
        
        return "redirect:/showArticle/"+id;
    }
	
	
	@GetMapping("/deleteArticle/{id}")
	public String supprimer(@PathVariable(value="id") int articleId, HttpSession session) {
		
		articleService.deleteArticle(articleId);
			
		session.setAttribute("delete","L'article  a bien supprimé");
		
		return "redirect:/list";
	}
}
