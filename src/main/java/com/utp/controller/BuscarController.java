/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.controller;

import com.utp.ejb.CategoriaFacadeLocal;
import com.utp.ejb.NotaFacadeLocal;
import com.utp.model.Categoria;
import com.utp.model.Nota;
import com.utp.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class BuscarController implements Serializable{
    
    @EJB
    private CategoriaFacadeLocal categoriaEJB;
    @EJB
    private NotaFacadeLocal notaEJB;
    private List<Categoria> listaCategorias;
    private List<Nota> listaNotas;
    private int codigoCategoria;
    private Date fechaConsulta;
    private String action;
    

    

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(int codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    
    
    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Nota> getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(List<Nota> listaNotas) {
        this.listaNotas = listaNotas;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    @PostConstruct
    public void init(){
        listaCategorias = categoriaEJB.findAll();
        Calendar cal = Calendar.getInstance();
        fechaConsulta = cal.getTime();
    }
   
    
    public void buscar(){
        try{
            Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listaNotas = notaEJB.buscar(us.getCodigo().getCodigo(), codigoCategoria, fechaConsulta);
            for(Nota n: listaNotas){
                if((n.getComentarioAdmin()!=null && !"".equals(n.getComentarioAdmin())) && n.getValoracion()!= null){
                    this.setAction("S");
                }
                else if((!"".equals(n.getComentarioAdmin())&& n.getComentarioAdmin()!=null) && n.getValoracion()==null){
                    this.setAction("C");
                }
                else if("".equals(n.getComentarioAdmin())&& n.getValoracion()!=null){
                    this.setAction("V");
                }
                else if(n.getComentarioAdmin()==null && n.getValoracion()==null){
                    this.setAction("");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
