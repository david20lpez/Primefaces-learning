
package com.utp.controller;

import com.utp.ejb.NotaFacadeLocal;
import com.utp.model.Nota;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ValorarController implements Serializable{
    
    @EJB
    private NotaFacadeLocal notaEJB;
    @Inject
    private Nota nota;
    @Inject
    private ComentarController comentarController;

    
    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }
    
   @PostConstruct
   public void init(){
       this.nota = comentarController.getNota();
   }
   
   public void registrar(){
       try{
           notaEJB.edit(nota);
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se registró el comentario y valoración"));
       }catch(Exception e){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error"));
       }finally{
           FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
       }
       
   }
    
}
