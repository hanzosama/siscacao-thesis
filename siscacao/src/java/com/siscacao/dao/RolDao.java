/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblRol;
import java.util.List;
/**
 *
 * @author Hanzo
 */
public interface RolDao {
    
    public List<TblRol> findAllRol();
    public boolean createRol(TblRol rol);
    public boolean updateRol(TblRol rol);
    public boolean deleteRol(Long id);
    
}
