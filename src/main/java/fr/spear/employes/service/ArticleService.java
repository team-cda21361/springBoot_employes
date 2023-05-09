package fr.spear.employes.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	public Optional<Article> getArticle(int id) {
		return articleRepo.findById(id);
	}
	
	//DELETE
	public boolean deleteArticle(int id) {
		Optional<Article> article =  articleRepo.findById(id);
		
		if(article.isPresent()) {
			
			articleRepo.delete(article.get());
			System.out.println("deleted " );
			return true;
		}
		return false;
	}
	
		//UPLOADS IMAGE
		public void uploadsImage(String uploadDir, String fileName,
	            MultipartFile multipartFile) throws IOException {
			
	        Path uploadPath = Paths.get(uploadDir);
	         
	        //Check if exist
	        if (!Files.exists(uploadPath)) {
	        	System.err.println("Not EXISTS");
	        	
	            Files.createDirectories(uploadPath);
	        }
	         
	        try  {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	            
	        } catch (IOException ioe) {        
	            throw new IOException("Oups : " + fileName, ioe);
	        }
	}
}
