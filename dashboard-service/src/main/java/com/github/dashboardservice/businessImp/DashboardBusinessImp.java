package com.github.dashboardservice.businessImp;

import com.github.dashboardservice.business.DashboardBusiness;
import com.github.dashboardservice.dao.DashboardRepository;
import com.github.dashboardservice.models.Dashboard;
import com.github.dashboardservice.models.Table1Data;
import com.github.dashboardservice.models.Table2;
import com.github.dashboardservice.models.Table2Data;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project dashboard-service
 */
@Service
public class DashboardBusinessImp implements DashboardBusiness {

    @Autowired
    DashboardRepository dashboardRepository;

    @PostConstruct
    public void start(){
        Dashboard dashboard = new Dashboard();

        dashboard.setGraphe1(
           Arrays.asList(
                Arrays.asList(100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100),
                Arrays.asList(80, 120, 105, 110, 95, 105, 90, 100, 80, 95, 70, 120),
                Arrays.asList(60, 80, 65, 130, 80, 105, 90, 130, 70, 115, 60, 130)
           ));

        dashboard.setGraphe2(Arrays.asList(80, 100, 70, 80, 120, 80));

        dashboard.setGraphe3(Arrays.asList(53, 20, 10, 80, 100, 45));

        dashboard.setGraphe4(Arrays.asList(90, 27, 60, 12, 80));

        Table1Data table1Data = new Table1Data();
        List<Table1Data> table1DataList = new ArrayList<>();

        table1Data.setName("Dakota Rice");
        table1Data.setCountry("Niger");
        table1Data.setCity("Oud-Turnhout");
        table1Data.setSalary("$36,738");

        table1DataList.add(table1Data);

        table1Data = new Table1Data();
        table1Data.setName("Minerva Hooper");
        table1Data.setCountry("CuraÃ§ao");
        table1Data.setCity("Sinaai-Waas");
        table1Data.setSalary("$23,789");
        table1DataList.add(table1Data);

        table1Data = new Table1Data();
        table1Data.setName("Sage Rodriguez");
        table1Data.setCountry("Netherlands");
        table1Data.setCity("Baileux");
        table1Data.setSalary("$56,142");
        table1DataList.add(table1Data);

        table1Data = new Table1Data();
        table1Data.setName("Philip Chaney");
        table1Data.setCountry("Korea, South");
        table1Data.setCity("Overland Park");
        table1Data.setSalary("$38,735");
        table1DataList.add(table1Data);

        table1Data = new Table1Data();
        table1Data.setName("Doris Greene");
        table1Data.setCountry("Malawi");
        table1Data.setCity("Feldkirchen in KÃ¤rnten");
        table1Data.setSalary("$63,542");
        table1DataList.add(table1Data);

        dashboard.setTable1Data(table1DataList);

        Table2Data table2Data = new Table2Data();
        List<Table2Data> table2DataList = new ArrayList<>();

        table2Data.setCheck(false);
        table2Data.setTitle("Update the Documentation");
        table2Data.setText("Dwuamish Head, Seattle, WA 8:47 AM");
        table2DataList.add(table2Data);

        table2Data = new Table2Data();
        table2Data.setCheck(true);
        table2Data.setTitle("GDPR Compliance");
        table2Data.setText("The GDPR is a regulation that requires businesses to\n" +
                "                      protect the personal data and privacy of Europe citizens\n" +
                "                      for transactions that occur within EU member states.");
        table2DataList.add(table2Data);

        table2Data = new Table2Data();
        table2Data.setCheck(false);
        table2Data.setTitle("Solve the issues");
        table2Data.setText("Fifty percent of all respondents said they would be more\n" +
                "                      likely to shop at a company");
        table2DataList.add(table2Data);

        table2Data.setCheck(false);
        table2Data.setTitle("Release v2.0.0");
        table2Data.setText("Ra Ave SW, Seattle, WA 98116, SUA 11:19 AM");
        table2DataList.add(table2Data);

        Table2 table2 = new Table2();
        table2.setTable2Data(table2DataList);
        table2.setTasks(5);
        dashboard.setTable2(table2);

        if(dashboardRepository.findAll()==null || dashboardRepository.findAll().size()==0)
        saveDashboarDb(dashboard);
    }

    public Dashboard getDashboard(){
        return dashboardRepository.findAll().get(0);
    }

    @Transactional
    public void saveDashboarDb(Dashboard dashboard){
        dashboardRepository.save(dashboard);
    }
}
