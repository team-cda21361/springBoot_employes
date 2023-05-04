package fr.spear.employes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.spear.employes.bean.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
