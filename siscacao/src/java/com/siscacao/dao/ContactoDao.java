/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblContacto;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface ContactoDao {
    public List<TblContacto> findAllContactosByClientId(Long id);
    public boolean createContacto(TblContacto contacto);
    public boolean updateContacto(TblContacto contacto);
}
