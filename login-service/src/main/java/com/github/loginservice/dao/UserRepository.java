package com.github.loginservice.dao;

import com.github.loginservice.models.UserDb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
public interface UserRepository extends MongoRepository<UserDb, String> {
	
	@Query(value = "{'userName': ?0, 'existe' : ?1}", fields = "{'motDePasse':0}")
	UserDb findOneByUserNameAndExisteExcludePassWord(String userName, boolean existe);
			
	@Query(value = "{}", fields = "{'motDePasse':0}")
	List<UserDb> findAll();
	
	@Query(value = "{'userName': ?0}", fields = "{'motDePasse':0}")
	UserDb findOneByUserNameExcludePassWord(String userName);
	
	UserDb findOneByUserName(String userName);


	
}
