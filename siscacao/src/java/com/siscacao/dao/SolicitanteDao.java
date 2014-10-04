/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblSolicitante;

/**
 *
 * @author Hanzo
 */
public interface SolicitanteDao {
 public TblSolicitante findSolicitanteByNumeroDeDocumento(TblSolicitante solicitante);
 public TblSolicitante findSolicitanteByNumeroDeDocumento(String documentNumber);
 public boolean CreateSolicitante(TblSolicitante solicitante);
 public boolean UpdateSolicitante(TblSolicitante solicitante);

}
