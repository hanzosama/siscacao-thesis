/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.PushDao;
import com.siscacao.dao.PushDaoImpl;
import com.siscacao.dao.SintomaDao;
import com.siscacao.dao.SintomaDaoImpl;
import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.model.TblImagen;
import com.siscacao.model.TblPushDevice;
import com.siscacao.model.TblSintoma;
import com.siscacao.model.TblSolicitud;
import com.siscacao.util.ImageNetIA;
import com.siscacao.util.SymptomIA;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@SessionScoped
public class SolicitudBean implements Serializable {

    private final Logger logger = Logger.getLogger(SolicitudBean.class);
    private ImageNetIA imageNetIA;
    private SymptomIA symptomIA;
    private SolicitudDao solicitudDao;
    private SintomaDao sintomasDao;
    private List<TblSolicitud> solicitudes;
    private List<TblSolicitud> filteredSolicitudes;
    private TblSolicitud selectedSolicitud;
    private List<TblImagen> selectedImagenes;
    private TblImagen selectedImagen;
    private String pathImage;
    private CroppedImage croppedImage;
    private String newImageName;
    private PieChartModel pieResultImage;
    private PieChartModel pieResultSymptom;
    private List<TblSintoma> sintomas;
    private String[] selectedSintomas;
    private PushServiceBean pushServiceBean;
    private String message;
    private PushDao pushDao;

    public SolicitudBean() {
        solicitudDao = new SolicitudDaoImpl();
        pushServiceBean = new PushServiceBean();
        pushDao = new PushDaoImpl();
        this.pieResultImage = new PieChartModel();
        this.pieResultSymptom = new PieChartModel();
        this.selectedSintomas = null;
        pieResultImage.set("", null);
        pieResultSymptom.set("", null);
        sintomasDao = new SintomaDaoImpl();
        sintomas = sintomasDao.getAllSintomas();
    }

    public List<TblSolicitud> getSolicitudes() {
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        Object id_usuario = session.getAttribute("id_usuario");
        solicitudes = solicitudDao.retrieveListSolicitudPendingForUser((Long) id_usuario);
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

    public List<TblSintoma> getSintomas() {
        return sintomas;
    }

    public String[] getSelectedSintomas() {
        return selectedSintomas;
    }

    public void setSelectedSintomas(String[] selectedSintomas) {
        this.selectedSintomas = selectedSintomas;
    }

    public TblImagen getSelectedImagen() {
        return selectedImagen;
    }

    public void setSelectedImagen(TblImagen selectedImagen) {
        this.selectedImagen = selectedImagen;
    }

    public String getNewImageName() {
        return newImageName;
    }

    public void setNewImageName(String newImageName) {
        this.newImageName = newImageName;
    }

    public PieChartModel getPieResult() {
        return pieResultImage;
    }

    public PieChartModel getPieResultSymptom() {
        return pieResultSymptom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void evaluateSintomas(ActionEvent actionEvent) {
        DataSet PruebaSet = new DataSet(14, 7);
        this.symptomIA = new SymptomIA();
        double[] sintomas = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (TblSintoma sintoma : this.sintomas) {
            for (int i = 0; i < this.selectedSintomas.length; i++) {
                if (sintoma.getDescripcionSintoma().equals(this.selectedSintomas[i])) {
                    sintomas[sintoma.getIdSintoma().intValue() - 1] = (double) 1;
                }
            }
        }


        PruebaSet.addRow(new DataSetRow(sintomas, new double[]{0, 0, 0, 0, 0, 0, 0}));
        Map<String, Double> symptom = this.symptomIA.getSymptom(PruebaSet);

        for (Map.Entry<String, Double> entry : symptom.entrySet()) {
            pieResultSymptom.set(entry.getKey(), entry.getValue());
        }

    }

    public void enviarDiagnostico(ActionEvent actionEvent) {
        TblPushDevice userPushDevice = pushDao.findPushByIdentification(this.selectedSolicitud.getTblSolicitante().getNumeroDocumento());
        if (userPushDevice != null) {
            pushServiceBean.doPushNotification(this.message, userPushDevice.getDeviceId());
        }
    }

    public String detalleSolicitud() {
        this.selectedSintomas = null;
        this.selectedImagen = null;
        this.newImageName = null;
        this.newImageName = null;
        this.pieResultImage.clear();
        this.pieResultImage.set("", null);
        this.pieResultSymptom.clear();
        this.pieResultSymptom.set("", null);
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

    public void crop(ActionEvent actionEvent) {
        String msg;
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
        Date dateCrope = new Date();

        setNewImageName(selectedImagen.getNombreImagen() + dateCrope.getTime() + "_crop.jpg");
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources/user/" + this.selectedSolicitud.getTblSolicitante().getNombreSolicitante().trim().toLowerCase().replace(" ", "") + this.selectedSolicitud.getTblSolicitante().getNumeroDocumento() + File.separator + getNewImageName();

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
            imageOutput.close();
            this.newImageName = "user/" + this.selectedSolicitud.getTblSolicitante().getNombreSolicitante().trim().toLowerCase().replace(" ", "") + this.selectedSolicitud.getTblSolicitante().getNumeroDocumento() + File.separator + getNewImageName();
        } catch (FileNotFoundException e) {
            logger.warn("Image file not found detail: " + e);
        } catch (IOException e) {
            logger.warn("Image File not write on system detail: " + e);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.warn("Sleep for crop image erro: " + e);
        }

    }

    public void guardarDiagnostico(ActionEvent actionEvent) {
        String msg;
        FacesMessage message;

        msg = "No ha generado ningun tipo de diagnostico para guardar";
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "Por favor seleccione un tipo de diagnostico y genere un analisis");
        FacesContext.getCurrentInstance().addMessage(msg, message);

    }

    public void analizeImage(ActionEvent actionEvent) {

        String msg;
        FacesMessage message;
        logger.info("Current imgage for analize : " + this.newImageName);
        if (this.newImageName == null || this.newImageName.equals("gfx/Imagen-animada-Lupa-10.png")) {
            msg = "Recorte la imagen seleccionada";
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "Por favor recorte la imagen para relizar el analisis");
            FacesContext.getCurrentInstance().addMessage(msg, message);
            return;
        }

        imageNetIA = new ImageNetIA();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        try {
            Map<String, Double> imageDiag = imageNetIA.getImageDiag(servletContext.getRealPath("") + File.separator + "resources/" + this.newImageName);
            for (Map.Entry<String, Double> entry : imageDiag.entrySet()) {
                pieResultImage.set(entry.getKey(), entry.getValue());
            }
        } catch (IOException ex) {
            logger.warn("Image File not write on system detail: " + ex);
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            logger.warn("Sleep for analize image erro: " + ex);
        }

    }
}
