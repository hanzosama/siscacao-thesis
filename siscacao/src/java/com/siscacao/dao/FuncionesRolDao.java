/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblFuncionRol;
import com.siscacao.model.TblRol;
import com.siscacao.model.TblUsuario;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface FuncionesRolDao {
    
public boolean createFuncionRol(TblFuncionRol tblFuncionRol);
 public List<TblFuncionRol> findFuncionRolByRol(TblRol tblRol);
 public boolean deleteFuncionRol(Long id);
}
