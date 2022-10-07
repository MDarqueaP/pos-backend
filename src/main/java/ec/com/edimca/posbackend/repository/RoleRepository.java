package ec.com.edimca.posbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.edimca.posbackend.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}