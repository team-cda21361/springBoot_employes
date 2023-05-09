package fr.spear.employes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.spear.employes.bean.Commentaire;
import fr.spear.employes.repository.CommentaireRepository;

@Service
public class CommentService {

	@Autowired
	private CommentaireRepository commentRepo;
	
	public void createComment(Commentaire comment) {
		
		commentRepo.save(comment);
	}
	
	public List<Commentaire> findAll(int id) {
		System.err.println(commentRepo.getComments(id));
	   // return commentRepo.findAll();
	    return commentRepo.getComments(id);
	}
}
