/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblUsuario;
import com.siscacao.model.TblUsuarioRol;

/**
 *
 * @author Hanzo
 */
public interface UsuarioRolDao {
public TblUsuarioRol retriveFuncionByuser(TblUsuario tblUsuario);    
}
