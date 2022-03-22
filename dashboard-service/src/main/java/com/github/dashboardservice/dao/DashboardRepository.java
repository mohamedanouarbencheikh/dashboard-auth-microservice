package com.github.dashboardservice.dao;

import com.github.dashboardservice.models.Dashboard;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project dashboard-service
 */
public interface DashboardRepository extends MongoRepository<Dashboard,String> {
}
