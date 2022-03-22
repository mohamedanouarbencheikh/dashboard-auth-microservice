package com.github.dashboardservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project dashboard-service
 */
@Data
@Document
public class Dashboard {
    @Id
    private String id;
    private List<List<Integer>> graphe1;
    private List<Integer> graphe2;
    private List<Integer> graphe3;
    private List<Integer> graphe4;
    private List<Table1Data> table1Data;
    private Table2 table2;
 }
