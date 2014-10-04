/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.DashboardDaoImpl;
import com.siscacao.dao.DepartamentoDao;
import com.siscacao.dao.DepartamentoDaoImpl;
import com.siscacao.model.TblDepartamento;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@SessionScoped
public class DashboardBean {

    private final Logger logger = Logger.getLogger(DashboardBean.class);
    private PieChartModel pieResultNeuroph;
    private PieChartModel pieResultNeurophByDepartament;
    private CartesianChartModel cartesianMadness;
    private DashboardModel model;
    private DashboardDaoImpl dashboardDaoImpl;
    private Long selectedDepartamento;
    private List<TblDepartamento> listDepartamentos;
    private DepartamentoDao departamentoDao;

    public DashboardBean() {
        this.departamentoDao = new DepartamentoDaoImpl();
        this.listDepartamentos = new ArrayList<TblDepartamento>(departamentoDao.findAllDepartamento());
        model = new DefaultDashboardModel();
        dashboardDaoImpl = new DashboardDaoImpl();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        column1.addWidget("Resultados");
        column2.addWidget("Resultados2");
        column3.addWidget("Resultados3");
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);

        this.pieResultNeuroph = new PieChartModel();
        this.pieResultNeurophByDepartament = new PieChartModel();
        this.pieResultNeuroph.set("", null);
        this.pieResultNeurophByDepartament.set("", null);

        this.cartesianMadness = new CartesianChartModel();
        ChartSeries madness = new ChartSeries();
        madness.setLabel("Enfermedades");
        madness.set("Monilia", 0);
        madness.set("Phytoptora", 0);
        madness.set("Escoba de Bruja", 0);
        madness.set("Machete", 0);
        madness.set("Bubas", 0);
        madness.set("Carpintero", 0);
        madness.set("Sanas", 0);
        this.cartesianMadness.addSeries(madness);

    }

    public PieChartModel getPieResultNeuroph() {
        return pieResultNeuroph;
    }

    public PieChartModel getPieResultNeurophByDepartament() {
        return pieResultNeurophByDepartament;
    }
    
    

    public DashboardModel getModel() {
        return model;
    }
    
    

    public CartesianChartModel getCartesianMadness() {
        return cartesianMadness;
    }

    public Long getSelectedDepartamento() {
        return selectedDepartamento;

    }

    public void setSelectedDepartamento(Long selectedDepartamento) {
        this.selectedDepartamento = selectedDepartamento;
    }

    public List<TblDepartamento> getListDepartamentos() {
        return listDepartamentos;
    }

    public void setListDepartamentos(List<TblDepartamento> listDepartamentos) {
        this.listDepartamentos = listDepartamentos;
    }

    public String viewDashBoard() {

        this.pieResultNeuroph.clear();
        this.pieResultNeurophByDepartament.clear();
        this.cartesianMadness.clear();
        this.pieResultNeuroph.set("", null);
        this.pieResultNeurophByDepartament.set("", null);

        Map<String, Double> generalReportNeuroph = dashboardDaoImpl.getGeneralReportNeuroph();
        for (Map.Entry<String, Double> entry : generalReportNeuroph.entrySet()) {
            this.pieResultNeuroph.set(entry.getKey(), entry.getValue());
        }
        if(selectedDepartamento!=null){
        Map<String, Double> generalReportNeuroph2 = dashboardDaoImpl.getGeneralReportNeurophByDepartament(selectedDepartamento);
        for (Map.Entry<String, Double> entry : generalReportNeuroph2.entrySet()) {
            this.pieResultNeurophByDepartament.set(entry.getKey(), entry.getValue());
        }
        }


        ChartSeries madness = new ChartSeries();
        madness.setLabel("Enfermedades");
        madness.set("Monilia", 0);
        madness.set("Phytoptora", 0);
        madness.set("Escoba de Bruja", 0);
        madness.set("Machete", 0);
        madness.set("Bubas", 0);
        madness.set("Carpintero", 0);
        madness.set("Sanas", 0);

        cartesianMadness.addSeries(madness);

        Map<String, Double> generalReportMadness = dashboardDaoImpl.getGeneralReportMadness();
        for (Map.Entry<String, Double> entry : generalReportMadness.entrySet()) {
            madness.set(entry.getKey(), entry.getValue());
        }

        return "dashboard/solicitudes_dashboard.xhtml?faces-redirect=true";
    }

    public Double getTotalRecords() {
        logger.info("Total record for reports: " + this.dashboardDaoImpl.getTotalRequestOnSystem());
        return this.dashboardDaoImpl.getTotalRequestOnSystem();

    }
    
    public void generateDepartamentResport(ActionEvent actionEvent){
    this.pieResultNeurophByDepartament.clear();
    this.pieResultNeurophByDepartament.set("", null);
    
      if(selectedDepartamento!=null){
        Map<String, Double> generalReportNeuroph2 = dashboardDaoImpl.getGeneralReportNeurophByDepartament(selectedDepartamento);
        for (Map.Entry<String, Double> entry : generalReportNeuroph2.entrySet()) {
            this.pieResultNeurophByDepartament.set(entry.getKey(), entry.getValue());
        }
        }
    
    }
}
