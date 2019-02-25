/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.ejb;

import com.utp.model.Telefono;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TelefonoFacade extends AbstractFacade<Telefono> implements TelefonoFacadeLocal{
    
    @PersistenceContext(unitName = "primePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TelefonoFacade() {
        super(Telefono.class);
    }
    
    @Override
    public List<Telefono> buscarTelefono(int codigoPersona)throws Exception{
        List<Telefono> lista = null;
        String consulta;
        try{
            consulta = "FROM Telefono t WHERE t.persona.codigo = ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, codigoPersona);
            
            lista = query.getResultList();
    
        }catch(Exception e){
            throw e;
        }
        return lista;
    }
    
}
