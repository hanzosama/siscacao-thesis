/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hanzo
 */
public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent pe) {
        FacesContext faceContext = pe.getFacesContext();
        String currentPage = faceContext.getViewRoot().getViewId();
        boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1) ? true : false;
        boolean isPotalPage = (currentPage.indexOf("portal") > -1) ? true : false;
        
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        Object usuario = session.getAttribute("usuario");
        if (session == null) {
            NavigationHandler nh = faceContext.getApplication().getNavigationHandler();
            nh.handleNavigation(faceContext, null, "login.xhtml?faces-redirect=true");
        }
        if (!isLoginPage && usuario == null && !isPotalPage) {
            NavigationHandler nh = faceContext.getApplication().getNavigationHandler();
            faceContext.getViewRoot().setViewId("");
            nh.handleNavigation(faceContext, null, "login.xhtml?faces-redirect=true");
        }

    }

    @Override
    public void beforePhase(PhaseEvent pe) {
        FacesContext facesContext = pe.getFacesContext();
        HttpServletResponse response = (HttpServletResponse) facesContext
                .getExternalContext().getResponse();
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        // Stronger according to blog comment below that references HTTP spec
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        // some date in the past
        response.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
