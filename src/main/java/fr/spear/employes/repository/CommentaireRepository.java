package fr.spear.employes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.spear.employes.bean.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
	
	@Query(value="SELECT com.id, com.content,com.article_id, com.user_id FROM commentaire com INNER JOIN article ar  ON com.article_id = ar.id WHERE ar.id=?1", nativeQuery = true)
	List<Commentaire> getComments(int id);


}
