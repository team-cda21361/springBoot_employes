package fr.spear.employes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.spear.employes.bean.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	User  findByEmail(String email);
}
