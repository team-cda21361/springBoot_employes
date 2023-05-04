package fr.spear.employes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.spear.employes.bean.User;
import fr.spear.employes.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	
	public boolean ajoutUser(User user) {
		BCryptPasswordEncoder encoder = 	new  BCryptPasswordEncoder();
    	user.setPassword(encoder.encode(user.getPassword()));

		userRepo.save(user);
		return true;
	}
}









