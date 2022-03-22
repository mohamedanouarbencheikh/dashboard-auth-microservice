package com.github.dashboardservice.models;

import lombok.Data;

import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project dashboard-service
 */
@Data
public class Table2 {
    private Integer tasks;
    private List<Table2Data> table2Data;
}
