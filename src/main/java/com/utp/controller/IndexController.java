
package com.utp.controller;

import com.utp.ejb.UsuarioFacadeLocal;
import com.utp.model.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

//@ManagedBean
@Named
@ViewScoped
public class IndexController implements Serializable{
    
    @EJB
    private UsuarioFacadeLocal EJBUsuario;
    @Inject
    private Usuario usuario;
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @PostConstruct
    public void init(){
        //usuario = new Usuario();
    }
    
    public String iniciarSesion(){
        String redireccion = null;
        Usuario us;
        try{
            us = EJBUsuario.iniciarSesion(usuario);
            if(us!=null){
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us);
                    redireccion ="/protegido/principal?faces-redirect=true";
                }else{
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Aviso","Usuario o clave erronea"));
                    }
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Aviso","Error"));
        }
        return redireccion;
    }    
    
}
