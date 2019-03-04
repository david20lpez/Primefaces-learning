/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.ejb;

import com.utp.model.Nota;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@Stateless
public class NotaFacade extends AbstractFacade<Nota> implements NotaFacadeLocal{
     
    @PersistenceContext(unitName = "primePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotaFacade() {
        super(Nota.class);
    }
    
    @Override
    public List<Nota> buscar(int codigoPersona, int codigoCategoria, Date fechaConsulta){
        List<Nota> lista;
        try{
            String jpql = "FROM Nota n WHERE n.persona.codigo = ?1 and n.categoria.codigo = ?2 and n.fecha between ?3 and ?4";
            Query query = em.createQuery(jpql);
            query.setParameter(1, codigoPersona);
            query.setParameter(2, codigoCategoria);
            query.setParameter(3, fechaConsulta, TemporalType.DATE);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaConsulta);
            cal.add(Calendar.DATE, 1);
            query.setParameter(4, cal, TemporalType.DATE);
            
            lista = query.getResultList();
        
        }catch(Exception e){
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<Nota> adminBuscar(int codigoCategoria, Date fechaConsulta){
        List<Nota> lista;
        try{
            String jpql = "FROM Nota n WHERE n.categoria.codigo = ?1 and n.fecha between ?2 and ?3";
            Query query = em.createQuery(jpql);
            query.setParameter(1, codigoCategoria);
            query.setParameter(2, fechaConsulta, TemporalType.DATE);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaConsulta);
            cal.add(Calendar.DATE, 1);
            query.setParameter(3, cal, TemporalType.DATE);
            
            lista = query.getResultList();
        
        }catch(Exception e){
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<Object []> buscarPorCategoria()throws Exception{
        List<Object []> lista;
        try{
            String jpql = "SELECT COUNT(n.codigo), n.categoria.codigo, n.fecha FROM Nota n GROUP BY n.categoria.codigo, CAST(n.fecha AS DATE)";
            Query query = em.createQuery(jpql);
           
            lista = query.getResultList();
        
        }catch(Exception e){
            throw e;
        }
        return lista;
    }
}
