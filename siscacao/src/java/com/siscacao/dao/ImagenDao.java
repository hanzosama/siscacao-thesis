/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblImagen;

/**
 *
 * @author Hanzo
 */
public interface ImagenDao {
public boolean createImagen(TblImagen imagen);
public boolean updateImagen(TblImagen imagen);
public TblImagen getImageById(Long id);
}
