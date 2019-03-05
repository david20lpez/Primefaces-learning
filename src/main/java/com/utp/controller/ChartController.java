/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.controller;

import com.utp.ejb.CategoriaFacadeLocal;
import com.utp.ejb.NotaFacadeLocal;
import com.utp.model.Categoria;
import com.utp.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean
public class ChartController implements Serializable {
 
    private LineChartModel lineModel2;
    private LineChartModel zoomModel;
    private BarChartModel barModel;
    @EJB
    private NotaFacadeLocal notaEJB;
    @EJB
    private CategoriaFacadeLocal categoriaEJB;
 
    @PostConstruct
    public void init() {
        createLineModels();
        createBarModels();
    }
 
 
    public LineChartModel getLineModel2() {
        return lineModel2;
    }
 
    public LineChartModel getZoomModel() {
        return zoomModel;
    }
    
    public BarChartModel getBarModel() {
        return barModel;
    }
 
 
 
    private LineChartModel initCategoryModel() throws Exception {
        LineChartModel model = new LineChartModel();
        try {
            
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            
            String dateString;
             
            Map<String, List<Object []>> map = new HashMap<>();
            List<Categoria> categorias = categoriaEJB.findAll();
            for(Categoria categoria : categorias){
                map.put(categoria.getNombre(), listaPorCategoria(categoria));
            }    
            
            Set< Map.Entry< String, List<Object []>> > st = map.entrySet();
            for(Map.Entry<String, List<Object []>> me: st){
               ChartSeries notas = new ChartSeries();
               notas.setLabel(me.getKey());
               for(Object [] obj : me.getValue()){
                   dateString = format.format((Date)obj[2]);
                   if(categoriaEJB.find((Integer)obj[1]).getNombre() == null ? me.getKey() == null : categoriaEJB.find((Integer)obj[1]).getNombre().equals(me.getKey())){
                       notas.set(dateString, (Long)obj[0]);
                   } 
               }
               model.addSeries(notas);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }
    
    private List<Object []> listaPorCategoria(Categoria categoria){
        List<Object []> lista = new ArrayList<>();
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        try{
            List<Object[]> results = notaEJB.buscarPorCategoria();
            List<Object[]> resultsAdmin = notaEJB.buscarPorCategoriaAdmin();
            for(Object [] obj : results){
                if(((Integer)obj[1] == categoria.getCodigo()) && us.getCodigo().getCodigo() == (Integer)obj[3]){
                    lista.add(obj);
                }
            }
            for(Object[] obj : resultsAdmin){
                if("A".equals(us.getTipo()) && (Integer)obj[1] == categoria.getCodigo()){
                    lista.add(obj);
                }
            }
        }catch(Exception e){
        }
        return lista;
    }
    
    private void createLineModels() {
        lineModel2 = initLinearModel();
        
        Axis yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
 
        try {
            lineModel2 = initCategoryModel();
        } catch (Exception ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        lineModel2.setTitle("Gráfico de categorias");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Fecha"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Número de notas");
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
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        try {
            
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            
            String dateString;
             
            Map<String, List<Object []>> map = new HashMap<>();
            List<Categoria> categorias = categoriaEJB.findAll();
            for(Categoria categoria : categorias){
                map.put(categoria.getNombre(), listaPorCategoria(categoria));
            }    
            
            Set< Map.Entry< String, List<Object []>> > st = map.entrySet();
            for(Map.Entry<String, List<Object []>> me: st){
               ChartSeries notas = new ChartSeries();
               notas.setLabel(me.getKey());
               for(Object [] obj : me.getValue()){
                   dateString = format.format((Date)obj[2]);
                   if(categoriaEJB.find((Integer)obj[1]).getNombre() == null ? me.getKey() == null : categoriaEJB.find((Integer)obj[1]).getNombre().equals(me.getKey())){
                       notas.set(dateString,(Long)obj[0]);
                   } 
               }
               model.addSeries(notas);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return model;
    }
 
    private void createBarModels() {
        createBarModel();
    }
 
    private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setTitle("Gráfico de barras");
        barModel.setLegendPosition("ne");
        barModel.setShowPointLabels(true);
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Fecha");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad de notas");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
     
}