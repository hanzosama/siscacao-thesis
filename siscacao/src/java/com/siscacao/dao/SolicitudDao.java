/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblSolicitud;
import com.siscacao.model.TblUsuario;
import java.util.List;

/**
 *
 * @author Hanzo
 */
public interface SolicitudDao {
  public boolean createSolicitud(TblSolicitud tblSolicitud);
  public boolean updateSolicitud(TblSolicitud tblSolicitud);
  public List<TblSolicitud> retrieveListSolicitudPending();
  public void signedSolicitud(TblSolicitud tblSolicitud,TblUsuario tblUsuario);
}
