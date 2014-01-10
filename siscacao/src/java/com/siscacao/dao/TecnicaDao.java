/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblTecnicaCultivo;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface TecnicaDao {
   public List<TblTecnicaCultivo> findAllTecnicaCultivo(); 
}
