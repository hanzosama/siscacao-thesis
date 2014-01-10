/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblTipoContacto;

/**
 *
 * @author Hanzo
 */
public interface TipoContactoDao {
    public TblTipoContacto findTipoContactoId(String nombreTipo);
}
