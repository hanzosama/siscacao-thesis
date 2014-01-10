/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblDepartamento;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface DepartamentoDao {
   public List<TblDepartamento> findAllDepartamento(); 
}
