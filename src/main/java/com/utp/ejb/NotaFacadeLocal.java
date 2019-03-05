/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.ejb;

import com.utp.model.Categoria;
import com.utp.model.Nota;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@Local
public interface NotaFacadeLocal {
    void create(Nota nota);

    void edit(Nota nota);

    void remove(Nota nota);

    Nota find(Object id);

    List<Nota> findAll();

    List<Nota> findRange(int[] range);

    int count();
    
    List<Nota> buscar(int codigoPersona, int codigoCategoria, Date fechaConsulta);
    
    List<Nota> adminBuscar(int codigoCategoria, Date fechaConsulta);
    
    List<Object []> buscarPorCategoria()throws Exception;
        
    List<Object []> buscarPorCategoriaAdmin()throws Exception;
}
