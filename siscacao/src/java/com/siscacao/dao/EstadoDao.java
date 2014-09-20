/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblEstado;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface EstadoDao {
    public List<TblEstado> findAllEstados();
    public TblEstado findEstadoById(Long id);
    public TblEstado findEstadoByName(String name);
}
