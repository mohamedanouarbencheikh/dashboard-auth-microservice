package com.github.dashboardservice.controller;

import com.github.dashboardservice.business.DashboardBusiness;
import com.github.dashboardservice.models.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project dashboard-service
 */
@RestController
public class DashboardController {

    @Autowired
    DashboardBusiness dashboardBusiness;

    @GetMapping(value = "/app/loadDataDashboard")
    public Dashboard loadDataDashboard() {
        return dashboardBusiness.getDashboard();
    }
}
