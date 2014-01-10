/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblVariedad;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface VariedadDao {
   public List<TblVariedad> findAllVariedad(); 
}
