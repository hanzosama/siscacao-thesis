/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.UsuarioDao;
import com.siscacao.dao.UsuarioDaoImpl;
import com.siscacao.model.TblUsuario;
import org.primefaces.context.RequestContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private TblUsuario usuario;
    private UsuarioDao usuarioDAO;
    private AppBean baseURL;

    public LoginBean() {
        this.baseURL = new AppBean();
        this.usuarioDAO = new UsuarioDaoImpl();
        if (this.usuario == null) {
            this.usuario = new TblUsuario();
        }
    }

    public TblUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TblUsuario usuario) {
        this.usuario = usuario;
    }

    public String login() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        boolean loggedIn;
        String redirect = "";
        this.usuario = this.usuarioDAO.login(this.usuario);
        if (this.usuario != null) {
            loggedIn = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.usuario.getCuenta());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("perfil", this.usuario.getRol());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id_usuario", this.usuario.getIdUsuario());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido ", this.usuario.getNombreUsuario());
            FacesContext.getCurrentInstance().getViewRoot().setViewId("");
            if(this.usuario.getRol().equals("Administrador")){
            redirect = "view/inicio.jsf?faces-redirect=true";
            }else if(this.usuario.getRol().equals("Consultor")){
            redirect = "view/revision.jsf?faces-redirect=true";    
            }else if(this.usuario.getRol().equals("Operador")){
            redirect = "view/registro.jsf?faces-redirect=true";    
            }
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario y/o clave incorrectos", "");
            if (this.usuario == null) {
                this.usuario = new TblUsuario();
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        return redirect;
    }

    public String logout() {
        String redirect = "";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.usuario = new TblUsuario();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        facesContext.getViewRoot().setViewId("");
        logoutSess();
        redirect = "login.xhtml?faces-redirect=true";
        return redirect;
    }

    public void logoutSess() {
       FacesContext faceContext = FacesContext.getCurrentInstance();
        String currentPage = faceContext.getViewRoot().getViewId();
        boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1) ? true : false;
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        Object usuario = session.getAttribute("usuario");
        if (session == null) {
            NavigationHandler nh = faceContext.getApplication().getNavigationHandler();
            nh.handleNavigation(faceContext, null, "login.xhtml?faces-redirect=true");
        }
        if (!isLoginPage && usuario == null) {
            NavigationHandler nh = faceContext.getApplication().getNavigationHandler();
            faceContext.getViewRoot().setViewId("");
            nh.handleNavigation(faceContext, null, "login.xhtml?faces-redirect=true");
        }
    }   
}
