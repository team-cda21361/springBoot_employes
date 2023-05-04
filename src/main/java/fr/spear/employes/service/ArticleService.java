package fr.spear.employes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.spear.employes.bean.Article;
import fr.spear.employes.repository.ArticleRepo;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepo articleRepo;
	
	public void ajoutArticle(Article article) {
		articleRepo.save(article);
	}
	
	public List<Article> getArticles(){
		return articleRepo.findAll();
	}
}
