/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblPushDevice;

/**
 *
 * @author Hanzo
 */
public interface PushDao {
  
    public TblPushDevice findPushByIdentification(String identification);
    public boolean createPushDevice(TblPushDevice pushDevice);
    public boolean deletePushDevice(Long id);
    public boolean updateRol(TblPushDevice pushDevice);
}
