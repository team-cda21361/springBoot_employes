package fr.spear.employes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.spear.employes.bean.Employes;

public interface EmployesRepository extends JpaRepository<Employes, Integer>{
	
	Employes  findByEmail(String email);

}
