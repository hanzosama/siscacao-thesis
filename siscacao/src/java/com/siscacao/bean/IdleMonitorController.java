/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.i18n.diccionario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Hanzo
 */
@ManagedBean
public class IdleMonitorController {

    private diccionario diccionario = new diccionario();

    public void idleListener() {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                this.diccionario.getString("wrn_session_expired"), this.diccionario.getString("wrn_session_expired_detail")));


        //invalidate session  
    }

    public void logoutsession() {
        LoginBean loBean = new LoginBean();
        loBean.logout();
    }

    public void activeListener() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                this.diccionario.getString("wrn_session_restored"), ""));
    }
}
