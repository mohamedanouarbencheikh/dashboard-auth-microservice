package com.github.gatewayservice.dao;

import com.github.gatewayservice.models.UserDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
public interface UserRepository extends MongoRepository<UserDb, String> {
	
	@Query(value = "{'userName': ?0, 'existe' : ?1}", fields = "{'motDePasse':0}")
    UserDb findOneByUserNameAndExisteExcludePassWord(String userName, boolean existe);
			
	@Query(value = "{}", fields = "{'motDePasse':0}")
	Page<UserDb> findAll(Pageable pageable);
	
	@Query(value = "{'userName': ?0}", fields = "{'motDePasse':0}")
    UserDb findOneByUserNameExcludePassWord(String userName);

	UserDb findOneByUserName(String userName);
}
