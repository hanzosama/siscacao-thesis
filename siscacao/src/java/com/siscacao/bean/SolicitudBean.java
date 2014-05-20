/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.model.TblImagen;
import com.siscacao.model.TblSolicitud;
import com.siscacao.util.ImageNetIA;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@SessionScoped
public class SolicitudBean implements Serializable {
    private ImageNetIA imageNetIA;
    private SolicitudDao solicitudDao;
    private List<TblSolicitud> solicitudes;
    private List<TblSolicitud> filteredSolicitudes;
    private TblSolicitud selectedSolicitud;
    private List<TblImagen> selectedImagenes;
    private TblImagen selectedImagen;
    private String pathImage;
    private CroppedImage croppedImage;
    private String newImageName;
    private PieChartModel pieResult;

    public TblImagen getSelectedImagen() {
        return selectedImagen;
    }

    public void setSelectedImagen(TblImagen selectedImagen) {
        this.selectedImagen = selectedImagen;
    }

    public SolicitudBean() {
        solicitudDao = new SolicitudDaoImpl();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        Object id_usuario = session.getAttribute("id_usuario");
        solicitudes = solicitudDao.retrieveListSolicitudPendingForUser((Long) id_usuario);
        this.pieResult = new PieChartModel();
        
        pieResult.set("",null);

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

    public String detalleSolicitud() {
        this.selectedImagen = null;
        this.newImageName = null;
        this.newImageName=null;
        this.pieResult.clear();
        this.pieResult.set("", null);
        return "solicitud_detalle/detalle_solicitud.jsf?faces-redirect=true";
    }

    public List<TblImagen> getSelectedImagenes() {
        this.selectedImagenes = new ArrayList<TblImagen>(this.selectedSolicitud.getTblImagens());
        return selectedImagenes;
    }

    public void setSelectedImagenes(List<TblImagen> selectedImagenes) {
        this.selectedImagenes = selectedImagenes;
    }

    public String getPathImage() {
        if (selectedImagen == null) {
            pathImage = "gfx/Imagen-animada-Lupa-10.png";
        } else {
            pathImage = "user/" + selectedImagen.getPathImagen();
        }

        if (this.newImageName == null) {
            this.newImageName = "gfx/Imagen-animada-Lupa-10.png";
        }
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public void crop(ActionEvent actionEvent) {
        String msg ;
        FacesMessage message;

        if (croppedImage == null) {
            return;
        }
        if (this.selectedImagen == null) {
            msg = "Seleccione una imagen";
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "Por favor seleccione una imagen para recortar");
            FacesContext.getCurrentInstance().addMessage(msg, message);
            return;
        }

        setNewImageName(selectedImagen.getNombreImagen() + "_crop.jpg");
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources/user/" + this.selectedSolicitud.getTblSolicitante().getNombreSolicitante().trim().toLowerCase().replace(" ", "") + this.selectedSolicitud.getTblSolicitante().getNumeroDocumento() + File.separator + getNewImageName();

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
            imageOutput.close();
            this.newImageName = "user/" + this.selectedSolicitud.getTblSolicitante().getNombreSolicitante().trim().toLowerCase().replace(" ", "") + this.selectedSolicitud.getTblSolicitante().getNumeroDocumento() + File.separator + getNewImageName();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

    }
    public void analizeImage(ActionEvent actionEvent) {
        
        String msg ;
        FacesMessage message;
        System.out.println(this.newImageName);
         if (this.newImageName == null || this.newImageName.equals("gfx/Imagen-animada-Lupa-10.png")) {
            msg = "Recorte la imagen seleccionada";
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "Por favor recorte la imagen para relizar el analisis");
            FacesContext.getCurrentInstance().addMessage(msg, message);
            return;
        }
        
        imageNetIA = new ImageNetIA();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        try {
            Map<String, Double> imageDiag = imageNetIA.getImageDiag(servletContext.getRealPath("") + File.separator + "resources/"+this.newImageName);
            for (Map.Entry<String,Double> entry: imageDiag.entrySet()){
                pieResult.set(entry.getKey(), entry.getValue());
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
         try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
       
    }

    public String getNewImageName() {
        return newImageName;
    }

    public void setNewImageName(String newImageName) {
        this.newImageName = newImageName;
    }

    public PieChartModel getPieResult() {
        return pieResult;
    }
    
    
}
