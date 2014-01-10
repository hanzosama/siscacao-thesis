/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.util;

/**
 *
 * @author Hanzo
 */

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LocaleBean {
  private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public LocaleBean() {
        this.locale = new Locale("es");
    }
 
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        this.locale = new Locale(language);
    }   
}
