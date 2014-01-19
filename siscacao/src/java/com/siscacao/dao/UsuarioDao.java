/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblUsuario;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface UsuarioDao {
    public TblUsuario findUserbyCuenta(TblUsuario Usuario);
    public TblUsuario login(TblUsuario Usuario);
    public List<TblUsuario> findAllUsers();
    public boolean createUser(TblUsuario usuario);
    public boolean updateUser(TblUsuario usuario);
    public boolean deleteUser(Long id);
    public boolean resetPwdUser(Long id, String Pwd);
    public TblUsuario retrieveLastUserToSolicitud();
}
