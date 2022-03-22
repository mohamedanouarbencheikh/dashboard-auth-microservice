package com.github.loginservice.dao;

import com.github.loginservice.models.SettingAdmin;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
public interface SettingAdminRepository extends MongoRepository<SettingAdmin, String> {

}
