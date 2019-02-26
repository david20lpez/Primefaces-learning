/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.controller;

import com.utp.ejb.NotaFacadeLocal;
import com.utp.model.Nota;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import javax.faces.view.ViewScoped;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

@ManagedBean(name="timelineController")
@ViewScoped
public class TimelineController implements Serializable{
    @EJB
    private NotaFacadeLocal notasEJB;
    private List<Nota> notas;
    
    private TimelineModel model;
   
    private boolean selectable = true;  
    private boolean zoomable = true;  
    private boolean moveable = true;  
    private boolean stackEvents = true;  
    private String eventStyle = "box";  
    private boolean axisOnTop;  
    private boolean showCurrentTime = true;  
    private boolean showNavigation = false;
    
    
   
    @PostConstruct 
    protected void initialize() {
        notas = notasEJB.findAll();
        model = new TimelineModel();  
        
        Calendar cal1;
   
//        Calendar cal = Calendar.getInstance();                   
//        cal.set(2014, Calendar.JUNE, 12, 0, 0, 0);  
//        model.add(new TimelineEvent("PrimeUI 1.1", cal.getTime())); 
        
        for(Nota n : notas){
            cal1 = Calendar.getInstance();
            cal1.setTime(n.getFecha());
            model.add(new TimelineEvent(n.getEncabezado(), cal1.getTime()));
        }     
    }  
    
   
    public void onSelect(TimelineSelectEvent e) {  
        TimelineEvent timelineEvent = e.getTimelineEvent();  
        
        //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Descripción de la nota:", timelineEvent.getData().toString());  
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        for(Nota n: notas){
            if(n.getEncabezado().equals(timelineEvent.getData().toString())){
                String valoracion = valorar(n.getValoracion());
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Comentario:", n.getComentarioAdmin());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valoración:", valoracion);
                FacesContext.getCurrentInstance().addMessage(null, msg1);
            }
        }
    } 
    
    public String valorar(int n){
        int i = 0;
        String valor = "";
        while(i < n){
            i++;
            valor = valor + "★";
        }
        return valor;
    }
   
    public TimelineModel getModel() {  
        return model;  
    }  
   
    public boolean isSelectable() {  
        return selectable;  
    }  
   
    public void setSelectable(boolean selectable) {  
        this.selectable = selectable;  
    }  
   
    public boolean isZoomable() {  
        return zoomable;  
    }  
   
    public void setZoomable(boolean zoomable) {  
        this.zoomable = zoomable;  
    }  
   
    public boolean isMoveable() {  
        return moveable;  
    }  
   
    public void setMoveable(boolean moveable) {  
        this.moveable = moveable;  
    }  
   
    public boolean isStackEvents() {  
        return stackEvents;  
    }  
   
    public void setStackEvents(boolean stackEvents) {  
        this.stackEvents = stackEvents;  
    }  
   
    public String getEventStyle() {  
        return eventStyle;  
    }  
   
    public void setEventStyle(String eventStyle) {  
        this.eventStyle = eventStyle;  
    }  
   
    public boolean isAxisOnTop() {  
        return axisOnTop;  
    }  
   
    public void setAxisOnTop(boolean axisOnTop) {  
        this.axisOnTop = axisOnTop;  
    }  
   
    public boolean isShowCurrentTime() {  
        return showCurrentTime;  
    }  
   
    public void setShowCurrentTime(boolean showCurrentTime) {  
        this.showCurrentTime = showCurrentTime;  
    }  
   
    public boolean isShowNavigation() {  
        return showNavigation;  
    }  
   
    public void setShowNavigation(boolean showNavigation) {  
        this.showNavigation = showNavigation;  
    }  
}
