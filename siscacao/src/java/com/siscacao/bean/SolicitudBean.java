/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.DepartamentoDao;
import com.siscacao.dao.DepartamentoDaoImpl;
import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.model.TblDepartamento;
import com.siscacao.model.TblImagen;
import com.siscacao.model.TblSolicitud;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.CroppedImage;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@RequestScoped
public class SolicitudBean implements Serializable{
    private SolicitudDao solicitudDao;
    private List<TblSolicitud> solicitudes;
    private List<TblSolicitud> filteredSolicitudes;
    private TblSolicitud selectedSolicitud;
    private List<TblImagen> selectedImagenes;
    
     private CroppedImage croppedImage;
     private TblImagen selectedImagen;

    public TblImagen getSelectedImagen() {
        return selectedImagen;
    }

    public void setSelectedImagen(TblImagen selectedImagen) {
        this.selectedImagen = selectedImagen;
    }

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public SolicitudBean() {
        solicitudDao = new SolicitudDaoImpl();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        Object id_usuario = session.getAttribute("id_usuario");
        solicitudes = solicitudDao.retrieveListSolicitudPendingForUser((Long)id_usuario);
    }
    
     public List<TblSolicitud> getSolicitudes() {
        return solicitudes;
    }

    public List<TblSolicitud> getFilteredSolicitudes() {
        return filteredSolicitudes;
    }

    public void setFilteredSolicitudes(List<TblSolicitud> filteredSolicitudes) {
        this.filteredSolicitudes = filteredSolicitudes;
    }

    public TblSolicitud getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(TblSolicitud selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }
    
    public String detalleSolicitud(){
        return "solicitud_detalle/detalle_solicitud.jsf";
    }

    public List<TblImagen> getSelectedImagenes() {
        this.selectedImagenes = new ArrayList<TblImagen>(this.selectedSolicitud.getTblImagens());
        return selectedImagenes;
    }

    public void setSelectedImagenes(List<TblImagen> selectedImagenes) {
        this.selectedImagenes = selectedImagenes;
    }
    
    
   
}
