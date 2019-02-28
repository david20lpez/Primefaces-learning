/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.controller;

import com.utp.ejb.NotaFacadeLocal;
import com.utp.model.Categoria;
import com.utp.model.Nota;
import com.utp.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean
public class ChartController implements Serializable {
 
    private LineChartModel lineModel2;
    private LineChartModel zoomModel;
    @EJB
    private NotaFacadeLocal notaEJB;
    private List<Nota> notas;
 
    @PostConstruct
    public void init() {
        notas = notaEJB.findAll();
        createLineModels();
    }
 
 
    public LineChartModel getLineModel2() {
        return lineModel2;
    }
 
    public LineChartModel getZoomModel() {
        return zoomModel;
    }
 
 
 
    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//        ChartSeries boys = new ChartSeries();
//        boys.setLabel("Boys");
//        boys.set("2004", 120);
//        boys.set("2005", 100);
//        boys.set("2006", 44);
//        boys.set("2007", 150);
//        boys.set("2008", 25);
// 
//        ChartSeries girls = new ChartSeries();
//        girls.setLabel("Girls");
//        girls.set("2004", 52);
//        girls.set("2005", 60);
//        girls.set("2006", 110);
//        girls.set("2007", 90);
//        girls.set("2008", 120);
        
        
//        ChartSeries nota = new ChartSeries();
//        nota.setLabel("Notas");
//        String dateString;
//        for(Nota n : notas){
//            dateString = format.format(n.getFecha());
//            nota.set(dateString, notesPerDay(n.getFecha(),1));
//        }
        String dateString;
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        for(Nota n: notas){
            ChartSeries nota = new ChartSeries();
            if(n.getPersona().getCodigo() == us.getCodigo().getCodigo()){
                nota.setLabel(n.getCategoria().getNombre());
                dateString = format.format(n.getFecha());
                nota.set(dateString, notesPerDay(n.getFecha(),n.getCategoria().getCodigo(),us.getCodigo().getCodigo()));
                model.addSeries(nota);
            }
        }
        //model.addSeries(boys);
        //model.addSeries(girls);
        //model.addSeries(nota);
 
        return model;
    }
    
    public int notesPerDay(Date date, int codigoCategoria, int codigoUsuario){
        List<Nota> notes;
        notes = notaEJB.buscar(codigoUsuario, codigoCategoria, date);
        return notes.size();
    }
 
    private void createLineModels() {
        lineModel2 = initLinearModel();
        
        Axis yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
 
        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(10);
 
        zoomModel = initLinearModel();
        zoomModel.setTitle("Zoom");
        zoomModel.setZoom(true);
        zoomModel.setLegendPosition("e");
        yAxis = zoomModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

 
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        return model;
    }
    
}