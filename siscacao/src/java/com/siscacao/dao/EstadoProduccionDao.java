/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblEstadoProduccion;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface EstadoProduccionDao {
    public List<TblEstadoProduccion> findAllEstadoProduccionDao();
}
