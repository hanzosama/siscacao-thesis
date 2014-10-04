/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblCultivo;

/**
 *
 * @author Hanzo
 */
public interface CultivoDao {
    public boolean createCultivo(TblCultivo tblCultivo);
    public TblCultivo getCultivoById(Long id);
    public TblCultivo getCultivo(TblCultivo cultivo);
}
