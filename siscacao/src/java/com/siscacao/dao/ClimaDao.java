/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblClima;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface ClimaDao {
    public List<TblClima> findAllClimas();
}
