package com.github.loginservice.dao;

import com.github.loginservice.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
public interface RoleRepository extends MongoRepository<Role, String>{

}
