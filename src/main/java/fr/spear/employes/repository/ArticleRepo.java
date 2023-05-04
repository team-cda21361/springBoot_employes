package fr.spear.employes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.spear.employes.bean.Article;

public interface ArticleRepo extends JpaRepository<Article, Integer> {

}
