/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblFunciones;
import com.siscacao.model.TblRol;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface FuncionesDao {
   public List<TblFunciones> findAllFunciones(); 
}
