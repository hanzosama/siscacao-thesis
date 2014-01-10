/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Hanzo
 */
@ManagedBean
public class IdleMonitorController {  
      
    public void idleListener() { 
       
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,   
                                        "Su session ha expirado", "Usted ha estado ausente por mas de 20 min"));
       
          
        //invalidate session  
    }  
    public void logoutsession(){
        LoginBean loBean= new LoginBean();
       loBean.logout();
    }
  
    public void activeListener() {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,  
                                        "Session reestablecida", ""));  
    }  
} 
