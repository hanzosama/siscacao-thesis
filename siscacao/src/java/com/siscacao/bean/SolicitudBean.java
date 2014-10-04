/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.ClimaDaoImpl;
import com.siscacao.dao.ContactoDao;
import com.siscacao.dao.ContactoDaoImpl;
import com.siscacao.dao.CultivoDao;
import com.siscacao.dao.CultivoDaoImpl;
import com.siscacao.dao.DiagnosticoDao;
import com.siscacao.dao.DiagnosticoDaoImpl;
import com.siscacao.dao.EstadoDao;
import com.siscacao.dao.EstadoDaoImpl;
import com.siscacao.dao.EstadoProduccionDaoImpl;
import com.siscacao.dao.ImagenDao;
import com.siscacao.dao.ImagenDaoImpl;
import com.siscacao.dao.PushDao;
import com.siscacao.dao.PushDaoImpl;
import com.siscacao.dao.SintomaDao;
import com.siscacao.dao.SintomaDaoImpl;
import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.dao.TecnicaDaoImpl;
import com.siscacao.dao.VariedadDaoImpl;
import com.siscacao.i18n.diccionario;
import com.siscacao.model.TblClima;
import com.siscacao.model.TblContacto;
import com.siscacao.model.TblCultivo;
import com.siscacao.model.TblDiagnostico;
import com.siscacao.model.TblDiagnosticoCaracteristica;
import com.siscacao.model.TblDiagnosticoImagen;
import com.siscacao.model.TblEstadoProduccion;
import com.siscacao.model.TblImagen;
import com.siscacao.model.TblPatologia;
import com.siscacao.model.TblPushDevice;
import com.siscacao.model.TblRespuestaSolicitud;
import com.siscacao.model.TblSintoma;
import com.siscacao.model.TblSolicitud;
import com.siscacao.model.TblTecnicaCultivo;
import com.siscacao.model.TblVariedad;
import com.siscacao.objects.json.SolicitudJson;
import com.siscacao.objects.json.SolicitudPortal;
import com.siscacao.services.SolicitudResource;
import com.siscacao.util.ImageNetIA;
import com.siscacao.util.SendMailSSL;
import com.siscacao.util.SymptomIA;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.catalina.tribes.util.Arrays;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
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
    public String newImageNameForSave;
    private String newImageNameWithoutPath;
    private PieChartModel pieResultImage;
    private PieChartModel pieResultSymptom;
    private CartesianChartModel cartesianChartModel;
    private List<TblSintoma> sintomas;
    private String[] selectedSintomas;
    private PushServiceBean pushServiceBean;
    private String message;
    private PushDao pushDao;
    private ImagenDao imagenDao;
    private DiagnosticoDao diagnosticoDao;
    private TblDiagnostico tblDiagnosticoById;
    private TblDiagnosticoImagen tblDiagnosticoImagenByGeneralDiagnotico;
    private ObjectMapper mapper = new ObjectMapper();
    private TblImagen cropImage;
    private TblDiagnosticoCaracteristica tblDiagnosticoCaracteristicaByGeneralDiagnotico;
    private boolean isSaving;
    private boolean isSavingResponse;
    private List<TblPatologia> patologias;
    private Long selectedPatologia;
    private EstadoDao estadoDao;
    private TblRespuestaSolicitud respuestaSolicitud;
    private TblPushDevice userPushDevice;
    private ContactoDao contactoDao;
    private TblContacto usuarioEmail;
    private SendMailSSL sendMailSSL;
    private String telefonoFijo;
    private String telefonoMovil;
    private diccionario diccionario;
    private CultivoDao cultivoDao;
    private TblCultivo cultivo;
    private String nombreClima;
    private String nombreVaridad;
    private String estadoProduccion;
    private String tecnicaCultivo;
    private List<TblClima> listClima;
    private List<TblVariedad> listVariedad;
    private List<TblTecnicaCultivo> listTecnica;
    private List<TblEstadoProduccion> listEstadoPro;
    private final ClimaDaoImpl climaDao;
    private final VariedadDaoImpl variedadDao;
    private final TecnicaDaoImpl tecnicaDao;
    private final EstadoProduccionDaoImpl estadoProduccionDao;
    private SolicitudPortal solicitudJson;

    public SolicitudBean() {
        solicitudDao = new SolicitudDaoImpl();
        pushServiceBean = new PushServiceBean();
        estadoDao = new EstadoDaoImpl();
        pushDao = new PushDaoImpl();
        diccionario = new diccionario();
        contactoDao = new ContactoDaoImpl();
        this.pieResultImage = new PieChartModel();
        this.pieResultSymptom = new PieChartModel();
        cartesianChartModel = new CartesianChartModel();
        this.sendMailSSL = new SendMailSSL();
        this.cultivoDao = new CultivoDaoImpl();
        ChartSeries image = new ChartSeries();
        image.setLabel("Imagen");
        image.set("Monilia", 0);
        image.set("Phytoptora", 0);
        image.set("Escoba de Bruja", 0);
        image.set("Machete", 0);
        image.set("Bubas", 0);
        image.set("Carpintero", 0);
        image.set("Sanas", 0);
        ChartSeries symptom = new ChartSeries();
        symptom.setLabel("Sintomas");
        symptom.set("Monilia", 0);
        symptom.set("Phytoptora", 0);
        symptom.set("Escoba de Bruja", 0);
        symptom.set("Machete", 0);
        symptom.set("Bubas", 0);
        symptom.set("Carpintero", 0);
        symptom.set("Sanas", 0);
        cartesianChartModel.addSeries(image);
        cartesianChartModel.addSeries(symptom);

        this.selectedSintomas = null;
        pieResultImage.set("", null);
        pieResultSymptom.set("", null);
        sintomasDao = new SintomaDaoImpl();
        sintomas = sintomasDao.getAllSintomas();
        this.imagenDao = new ImagenDaoImpl();
        this.diagnosticoDao = new DiagnosticoDaoImpl();
        this.climaDao = new ClimaDaoImpl();
        this.variedadDao = new VariedadDaoImpl();
        this.tecnicaDao = new TecnicaDaoImpl();
        this.estadoProduccionDao = new EstadoProduccionDaoImpl();
        this.patologias = diagnosticoDao.getAllPatolgias();
        this.nombreClima = this.diccionario.getString("lbl_info_not_registered");
        this.nombreVaridad = this.diccionario.getString("lbl_info_not_registered");
        this.estadoProduccion = this.diccionario.getString("lbl_info_not_registered");
        this.tecnicaCultivo = this.diccionario.getString("lbl_info_not_registered");

        this.listClima = new ArrayList<TblClima>(climaDao.findAllClimas());
        this.listVariedad = new ArrayList<TblVariedad>(variedadDao.findAllVariedad());
        this.listTecnica = new ArrayList<TblTecnicaCultivo>(tecnicaDao.findAllTecnicaCultivo());
        this.listEstadoPro = new ArrayList<TblEstadoProduccion>(estadoProduccionDao.findAllEstadoProduccionDao());

        if (this.cultivo == null) { // when info is not available
            this.cultivo = new TblCultivo();
            this.cultivo.setEdadCultivo(this.diccionario.getString("lbl_info_not_registered"));
            this.cultivo.setExtensionCultivo(this.diccionario.getString("lbl_info_not_registered"));
            this.cultivo.setNombreCultivo(this.diccionario.getString("lbl_info_not_registered"));
        }
    }

    public List<TblSolicitud> getSolicitudes() {
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        Object id_usuario = session.getAttribute("id_usuario");
        solicitudes = solicitudDao.retrieveListSolicitudPendingForUser((Long) id_usuario);
        return solicitudes;
    }

    public List<TblSolicitud> getSolicitudesSolicitante() {
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        Object id_usuario = session.getAttribute("id_solicitante");
        solicitudes = solicitudDao.retrieveListSolicitudForSolicitante((Long) id_usuario);
        return solicitudes;
    }

    public boolean isIsSaving() {
        return isSaving;
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

    public String getNewImageNameWithoutPath() {
        return newImageNameWithoutPath;
    }

    public void setNewImageNameWithoutPath(String newImageNameWithoutPath) {
        this.newImageNameWithoutPath = newImageNameWithoutPath;
    }

    public PieChartModel getPieResult() {
        return pieResultImage;
    }

    public PieChartModel getPieResultSymptom() {
        return pieResultSymptom;
    }

    public CartesianChartModel getCartesianChartModel() {
        return cartesianChartModel;
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

    public List<TblImagen> getSelectedImagenes() {
        this.selectedImagenes = new ArrayList<TblImagen>(this.selectedSolicitud.getTblImagens());
        return selectedImagenes;
    }

    public void setSelectedImagenes(List<TblImagen> selectedImagenes) {
        this.selectedImagenes = selectedImagenes;
    }

    public List<TblPatologia> getPatologias() {
        return patologias;
    }

    public void setPatologias(List<TblPatologia> patologias) {
        this.patologias = patologias;
    }

    public Long getSelectedPatologia() {
        return selectedPatologia;
    }

    public void setSelectedPatologia(Long selectedPatologia) {
        this.selectedPatologia = selectedPatologia;
    }

    public boolean isIsSavingResponse() {
        return isSavingResponse;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public TblContacto getUsuarioEmail() {
        return usuarioEmail;
    }

    public TblCultivo getCultivo() {
        return cultivo;
    }

    public String getNombreClima() {
        return nombreClima;
    }

    public String getNombreVaridad() {
        return nombreVaridad;
    }

    public String getEstadoProduccion() {
        return estadoProduccion;
    }

    public String getTecnicaCultivo() {
        return tecnicaCultivo;
    }

    public SolicitudPortal getSolicitudJson() {
        return solicitudJson;
    }

    
    public boolean isIsSavingResponseAndHaveMedia() {
        if (!isSavingResponse) {
            List<TblContacto> usuarioContactos = this.contactoDao.findAllContactosByClientId(this.selectedSolicitud.getTblSolicitante().getIdSolicitante());
            usuarioEmail = null;
            if (usuarioContactos != null) {
                for (TblContacto contacto : usuarioContactos) {
                    if (contacto.getTblTipoContacto().getNombreTipo().equals("EM")) {
                        this.usuarioEmail = contacto;
                    }
                    if (contacto.getTblTipoContacto().getNombreTipo().equals("TF")) {
                        this.telefonoFijo = contacto.getContacto();
                    }
                    if (contacto.getTblTipoContacto().getNombreTipo().equals("TM")) {
                        this.telefonoMovil = contacto.getContacto();
                    }
                }
            }
            /* if (userPushDevice != null || usuarioEmail != null) {
             return false;
             }
             */
            return false;
        }
        return true;
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

    public void evaluateSintomas(ActionEvent actionEvent) {
        DataSet PruebaSet = new DataSet(14, 7);
        this.symptomIA = new SymptomIA();
        double[] sintomas = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (TblSintoma sintoma : this.sintomas) {
            for (int i = 0; i < this.selectedSintomas.length; i++) {
                logger.info("Selected Sympton: " + this.selectedSintomas[i]);
                if (sintoma.getDescripcionSintoma().equals(this.selectedSintomas[i])) {
                    sintomas[sintoma.getIdSintoma().intValue() - 1] = (double) 1;
                }
            }
        }


        PruebaSet.addRow(new DataSetRow(sintomas, new double[]{0, 0, 0, 0, 0, 0, 0}));
        Map<String, Double> symptom = this.symptomIA.getSymptom(PruebaSet);
        pieResultSymptom.clear();
        for (Map.Entry<String, Double> entry : symptom.entrySet()) {
            pieResultSymptom.set(entry.getKey(), entry.getValue());
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
        this.cartesianChartModel.clear();
        ChartSeries image = new ChartSeries();
        image.setLabel("Imagen");
        image.set("Monilia", 0);
        image.set("Phytoptora", 0);
        image.set("Escoba de Bruja", 0);
        image.set("Machete", 0);
        image.set("Bubas", 0);
        image.set("Carpintero", 0);
        image.set("Sanas", 0);
        ChartSeries symptom = new ChartSeries();
        symptom.setLabel("Sintomas");
        symptom.set("Monilia", 0);
        symptom.set("Phytoptora", 0);
        symptom.set("Escoba de Bruja", 0);
        symptom.set("Machete", 0);
        symptom.set("Bubas", 0);
        symptom.set("Carpintero", 0);
        symptom.set("Sanas", 0);
        cartesianChartModel.addSeries(image);
        cartesianChartModel.addSeries(symptom);

        this.pieResultSymptom.set("", null);
        this.tblDiagnosticoById = null;
        this.tblDiagnosticoImagenByGeneralDiagnotico = null;
        this.tblDiagnosticoCaracteristicaByGeneralDiagnotico = null;
        this.respuestaSolicitud = null;
        this.selectedPatologia = null;
        this.cropImage = null;

        this.newImageName = null;
        this.newImageNameForSave = null;
        this.newImageNameWithoutPath = null;
        this.isSaving = true;
        this.isSavingResponse = true;
        //this.patologias = null;
        this.userPushDevice = null;
        this.usuarioEmail = null;
        this.telefonoFijo = "";
        this.telefonoMovil = "";

        this.message = this.diccionario.getString("template_send_push_and_email");

        //load data from data base

        List<TblContacto> usuarioContactos = this.contactoDao.findAllContactosByClientId(this.selectedSolicitud.getTblSolicitante().getIdSolicitante());
        if (usuarioContactos != null) {
            for (TblContacto contacto : usuarioContactos) {
                if (contacto.getTblTipoContacto().getNombreTipo().equals("EM")) {
                    this.usuarioEmail = contacto;
                }
                if (contacto.getTblTipoContacto().getNombreTipo().equals("TF")) {
                    this.telefonoFijo = contacto.getContacto();
                }
                if (contacto.getTblTipoContacto().getNombreTipo().equals("TM")) {
                    this.telefonoMovil = contacto.getContacto();
                }
            }
        }

        //load info cultivo

        if (this.selectedSolicitud.getTblCultivo() != null) {
            this.cultivo = this.cultivoDao.getCultivo(this.selectedSolicitud.getTblCultivo());
            if (this.cultivo.getEdadCultivo()==null || this.cultivo.getEdadCultivo().trim().equals("") ) {
                this.cultivo.setEdadCultivo(this.diccionario.getString("lbl_info_not_registered"));
            }
            if (this.cultivo.getExtensionCultivo()==null || this.cultivo.getExtensionCultivo().trim().equals("")) {
                this.cultivo.setExtensionCultivo(this.diccionario.getString("lbl_info_not_registered"));
            }
            if (this.cultivo.getNombreCultivo()==null || this.cultivo.getNombreCultivo().trim().equals("")) {
                this.cultivo.setNombreCultivo(this.diccionario.getString("lbl_info_not_registered"));
            }

            if (this.cultivo.getIdClima() != null) {
                for (TblClima clima : listClima) {
                    if (clima.getIdClima().equals(this.cultivo.getIdClima())) {
                        this.nombreClima = clima.getNombreClima();
                        break;
                    }
                }
            }
            if (this.cultivo.getTblVariedad() != null) {
                this.nombreVaridad = this.cultivo.getTblVariedad().getNombreVariedad();
            }
            if (this.cultivo.getTblEstadoProduccion() != null) {
                this.estadoProduccion = this.cultivo.getTblEstadoProduccion().getDescripcionEstado();
            }
            if (this.cultivo.getTblTecnicaCultivo() != null) {
                this.tecnicaCultivo = this.cultivo.getTblTecnicaCultivo().getNombreTecnica();
            }

        }

        userPushDevice = pushDao.findPushByIdentification(this.selectedSolicitud.getTblSolicitante().getNumeroDocumento());

        tblDiagnosticoById = diagnosticoDao.getTblDiagnosticoById(this.selectedSolicitud.getIdDiagnostico());
        respuestaSolicitud = diagnosticoDao.GetRespuestaSolicitudByIdSolicitud(this.selectedSolicitud.getIdSolicitud());
        if (respuestaSolicitud != null) {
            this.selectedPatologia = this.tblDiagnosticoById.getIdPatologia();
            this.message = this.tblDiagnosticoById.getDescripcionDiagnostico();
            this.isSavingResponse = false;
        }
        if (tblDiagnosticoById != null) {
            tblDiagnosticoImagenByGeneralDiagnotico = diagnosticoDao.getTblDiagnosticoImagenByGeneralDiagnotico(tblDiagnosticoById);
            if (tblDiagnosticoImagenByGeneralDiagnotico != null) {
                logger.info("Diagnostico image  saved.." + tblDiagnosticoImagenByGeneralDiagnotico.getIdImagen());
                String mapPie = tblDiagnosticoImagenByGeneralDiagnotico.getMapPie();
                Map<String, Number> map = new HashMap<String, Number>();
                try {
                    map = mapper.readValue(mapPie, new TypeReference<HashMap<String, Number>>() {
                    });
                } catch (IOException ex) {
                    logger.error(ex);
                }
                this.pieResultImage.setData(map);
                for (Map.Entry<String, Number> entry : this.pieResultImage.getData().entrySet()) {
                    Double parcentage = ((Double) entry.getValue()) * 100;
                    image.set(entry.getKey().trim(), parcentage);
                }
                cropImage = imagenDao.getImageById(tblDiagnosticoImagenByGeneralDiagnotico.getIdImagen());
                this.isSaving = false;
                this.newImageName = "user/" + this.selectedSolicitud.getTblSolicitante().getNombreSolicitante().trim().toLowerCase().replace(" ", "") + this.selectedSolicitud.getTblSolicitante().getNumeroDocumento() + File.separator + cropImage.getNombreImagen();
            }
            //load data for Symp
            tblDiagnosticoCaracteristicaByGeneralDiagnotico = diagnosticoDao.getTblDiagnosticoCaracteristicaByGeneralDiagnotico(tblDiagnosticoById);

            if (tblDiagnosticoCaracteristicaByGeneralDiagnotico != null) {
                String mapPie = tblDiagnosticoCaracteristicaByGeneralDiagnotico.getMapPie();
                Map<String, Number> map = new HashMap<String, Number>();
                try {
                    map = mapper.readValue(mapPie, new TypeReference<HashMap<String, Number>>() {
                    });
                } catch (IOException ex) {
                    logger.error(ex);
                }
                this.pieResultSymptom.setData(map);
                for (Map.Entry<String, Number> entry : this.pieResultSymptom.getData().entrySet()) {
                    Double parcentage = ((Double) entry.getValue()) * 100;
                    symptom.set(entry.getKey().trim(), parcentage);
                }
                this.selectedSintomas = tblDiagnosticoCaracteristicaByGeneralDiagnotico.getMapSintoma().replace("{", "").replace("}", "").split(",");
                for (int i = 0; i < this.selectedSintomas.length; i++) {
                    this.selectedSintomas[i] = this.selectedSintomas[i].trim();

                }
                logger.info("Saving Symptom: " + Arrays.toString(selectedSintomas));
                this.isSaving = false;
            }
        }

        //end of load data
        return "solicitud_detalle/detalle_solicitud.jsf?faces-redirect=true";
    }
    
    public String detalleSolicitudPortal() {
        SolicitudResource resource = new SolicitudResource();
        solicitudJson = new SolicitudPortal();
        solicitudJson.numeroSolicitud=this.selectedSolicitud.getSerial();
        solicitudJson=(SolicitudPortal) resource.getSolicitud(solicitudJson);
        logger.info("Solicitud portal: "+solicitudJson.toString());
        
        if(solicitudJson.getRecomendaciones().contains("$PATOLOGIA") && solicitudJson.getRecomendaciones().contains("$NOMBRE_SOLICITANTE")){
        solicitudJson.recomendaciones=solicitudJson.getRecomendaciones().replace("$PATOLOGIA", solicitudJson.getDiagnostico()).replace("$NOMBRE_SOLICITANTE", solicitudJson.getNombreSolicitante());
        }
        //return "detalle_solicitud.jsf?faces-redirect=true";
        return "";
    }
    public void crop(ActionEvent actionEvent) {
        String msg;
        FacesMessage message;

        if (croppedImage == null) {
            return;
        }
        if (this.selectedImagen == null) {
            msg = this.diccionario.getString("lbl_select_image");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, diccionario.getString("lbl_select_image_detail"));
            FacesContext.getCurrentInstance().addMessage(msg, message);
            return;
        }
        Date dateCrope = new Date();

        setNewImageName(selectedImagen.getNombreImagen() + dateCrope.getTime() + "_crop.jpg");
        newImageNameForSave = this.selectedSolicitud.getTblSolicitante().getNombreSolicitante().trim().toLowerCase().replace(" ", "") + this.selectedSolicitud.getTblSolicitante().getNumeroDocumento() + File.separator + getNewImageName();
        setNewImageNameWithoutPath(selectedImagen.getNombreImagen() + dateCrope.getTime() + "_crop.jpg");
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
        boolean isPieEmpty = false;
        Map<String, Number> dataImage = this.pieResultImage.getData();
        Map<String, Number> dataSymptom = this.pieResultSymptom.getData();
        if (dataImage.size() == 1 && dataSymptom.size() == 1) {
            if (dataImage.containsKey("") && dataSymptom.containsKey("")) {
                isPieEmpty = true;
            }
        }
        if (isPieEmpty) {

            msg = this.diccionario.getString("wrn_generate_diagnostic");
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, this.diccionario.getString("wrn_generate_diagnostic_detail"));
            FacesContext.getCurrentInstance().addMessage(msg, message);
            return;
        }

        //guardar diagnostico imagen
        logger.info("values on data pie image: " + dataImage.toString());
        this.cartesianChartModel.clear();
        this.selectedSolicitud.setTblEstado(estadoDao.findEstadoByName("ASIGNADO"));
        if (!dataImage.containsKey("")) {
            saveImageDiag();
        }
        if (!dataSymptom.containsKey("")) {
            saveSymptomDiag();
        }
        solicitudDao.updateSolicitud(selectedSolicitud);

        msg = this.diccionario.getString("lbl_generate_diagnostic_successfully");
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, this.diccionario.getString("lbl_generate_diagnostic_successfully_detail"));
        FacesContext.getCurrentInstance().addMessage(msg, message);
        this.isSaving = false;


    }

    private void saveImageDiag() {
        String json = "";
        ChartSeries image = new ChartSeries();
        image.setLabel("Imagen");
        image.set("Monilia", 0);
        image.set("Phytoptora", 0);
        image.set("Escoba de Bruja", 0);
        image.set("Machete", 0);
        image.set("Bubas", 0);
        image.set("Carpintero", 0);
        image.set("Sanas", 0);

        if (cropImage == null && tblDiagnosticoImagenByGeneralDiagnotico == null) {
            cropImage = new TblImagen();
            cropImage.setPathImagen(newImageNameForSave);
            cropImage.setNombreImagen(this.newImageNameWithoutPath);
            cropImage.setTblSolicitud(this.selectedSolicitud);
            logger.info("Saving image...");
            imagenDao.createImagen(cropImage);

            //save image and pie map relation;
            tblDiagnosticoById = new TblDiagnostico();
            diagnosticoDao.createDiagnosticoGeneral(tblDiagnosticoById);
            this.selectedSolicitud.setIdDiagnostico(tblDiagnosticoById.getIdDiagnostico());
            solicitudDao.updateSolicitud(selectedSolicitud);

            try {
                json = mapper.writeValueAsString(this.pieResultImage.getData());
            } catch (IOException ex) {
                logger.info(ex);
            }
            Map.Entry<String, Number> maxValuesImage = getMaxValuesImage(this.pieResultImage.getData());
            logger.info("Max value and Symptom :" + maxValuesImage.getKey() + " = " + maxValuesImage.getValue());

            TblDiagnosticoImagen diagnosticoImagen = new TblDiagnosticoImagen();
            diagnosticoImagen.setIdImagen(cropImage.getIdImagen());
            diagnosticoImagen.setMapPie(json);
            diagnosticoImagen.setTblDiagnosticoByIdDiagnostico(tblDiagnosticoById);
            if (maxValuesImage != null) {
                logger.info("Max value and Symptom :" + maxValuesImage.getKey() + " = " + maxValuesImage.getValue());
                diagnosticoImagen.setIdPatogologia(getIdPatologia(maxValuesImage.getKey()));
                diagnosticoImagen.setMaxValue((Double) maxValuesImage.getValue());
                for (Map.Entry<String, Number> entry : this.pieResultImage.getData().entrySet()) {
                    Double parcentage = ((Double) entry.getValue()) * 100;
                    image.set(entry.getKey(), parcentage);
                }
            }
            diagnosticoDao.createDiagnosticoImagen(diagnosticoImagen);
        } else { //update proccess  
            if (newImageNameForSave != null && newImageNameWithoutPath != null) {
                cropImage.setPathImagen(newImageNameForSave);
                cropImage.setNombreImagen(this.newImageNameWithoutPath);
                logger.info("Saving image...");
                imagenDao.updateImagen(cropImage);
            }


            try {
                json = mapper.writeValueAsString(this.pieResultImage.getData());
            } catch (IOException ex) {
                logger.info(ex);
            }
            Map.Entry<String, Number> maxValuesImage = getMaxValuesImage(this.pieResultImage.getData());
            if (maxValuesImage != null) {
                logger.info("Max value and Symptom :" + maxValuesImage.getKey() + " = " + maxValuesImage.getValue());
                this.tblDiagnosticoImagenByGeneralDiagnotico.setIdPatogologia(getIdPatologia(maxValuesImage.getKey()));
                this.tblDiagnosticoImagenByGeneralDiagnotico.setMaxValue((Double) maxValuesImage.getValue());
                for (Map.Entry<String, Number> entry : this.pieResultImage.getData().entrySet()) {
                    Double parcentage = ((Double) entry.getValue()) * 100;
                    image.set(entry.getKey(), parcentage);
                }
            }
            tblDiagnosticoImagenByGeneralDiagnotico.setMapPie(json);
            diagnosticoDao.updateDiagnosticoImage(tblDiagnosticoImagenByGeneralDiagnotico);
        }

        cartesianChartModel.addSeries(image);
    }

    private void saveSymptomDiag() {
        String json = "";
        ChartSeries symptom = new ChartSeries();
        symptom.setLabel("Sintomas");
        symptom.set("Monilia", 0);
        symptom.set("Phytoptora", 0);
        symptom.set("Escoba de Bruja", 0);
        symptom.set("Machete", 0);
        symptom.set("Bubas", 0);
        symptom.set("Carpintero", 0);
        symptom.set("Sanas", 0);
        if (tblDiagnosticoCaracteristicaByGeneralDiagnotico == null) {
            if (tblDiagnosticoById == null) {
                tblDiagnosticoById = new TblDiagnostico();
                diagnosticoDao.createDiagnosticoGeneral(tblDiagnosticoById);
                this.selectedSolicitud.setIdDiagnostico(tblDiagnosticoById.getIdDiagnostico());
                solicitudDao.updateSolicitud(selectedSolicitud);
            }

            try {
                json = mapper.writeValueAsString(this.pieResultSymptom.getData());
            } catch (IOException ex) {
                logger.info(ex);
            }
            Map.Entry<String, Number> maxValuesSymptom = getMaxValuesImage(this.pieResultSymptom.getData());

            TblDiagnosticoCaracteristica caracteristica = new TblDiagnosticoCaracteristica();
            caracteristica.setMapPie(json);
            caracteristica.setMapSintoma(Arrays.toString(selectedSintomas));
            caracteristica.setTblDiagnosticoByIdDiagnostico(tblDiagnosticoById);
            if (maxValuesSymptom != null) {
                logger.info("Max value and Symptom :" + maxValuesSymptom.getKey() + " = " + maxValuesSymptom.getValue());
                caracteristica.setIdPatogologia(getIdPatologia(maxValuesSymptom.getKey()));
                caracteristica.setMaxValue((Double) maxValuesSymptom.getValue());
                for (Map.Entry<String, Number> entry : this.pieResultSymptom.getData().entrySet()) {
                    Double parcentage = ((Double) entry.getValue()) * 100;
                    symptom.set(entry.getKey(), parcentage);
                }
            }
            diagnosticoDao.createDiagnosticoSintoma(caracteristica);
        } else {
            //update process            
            try {
                json = mapper.writeValueAsString(this.pieResultSymptom.getData());
            } catch (IOException ex) {
                logger.info(ex);
            }
            Map.Entry<String, Number> maxValuesSymptom = getMaxValuesImage(this.pieResultSymptom.getData());
            if (maxValuesSymptom != null) {
                logger.info("Max value and Symptom :" + maxValuesSymptom.getKey() + " = " + maxValuesSymptom.getValue());
                this.tblDiagnosticoCaracteristicaByGeneralDiagnotico.setIdPatogologia(getIdPatologia(maxValuesSymptom.getKey()));
                this.tblDiagnosticoCaracteristicaByGeneralDiagnotico.setMaxValue((Double) maxValuesSymptom.getValue());

                for (Map.Entry<String, Number> entry : this.pieResultSymptom.getData().entrySet()) {
                    Double parcentage = ((Double) entry.getValue()) * 100;
                    symptom.set(entry.getKey(), parcentage);
                }

            }
            this.tblDiagnosticoCaracteristicaByGeneralDiagnotico.setMapPie(json);
            this.tblDiagnosticoCaracteristicaByGeneralDiagnotico.setMapSintoma(Arrays.toString(selectedSintomas));
            diagnosticoDao.updateDiagnosticoImage(tblDiagnosticoCaracteristicaByGeneralDiagnotico);
        }
        this.cartesianChartModel.addSeries(symptom);
    }

    public Map.Entry<String, Number> getMaxValuesImage(Map<String, Number> pieData) {

        Map.Entry<String, Number> maxEntry = null;

        for (Map.Entry<String, Number> entry : pieData.entrySet()) {
            if (maxEntry == null || ((Double) entry.getValue() > (Double) maxEntry.getValue())) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    public Long getIdPatologia(String name) {

        for (TblPatologia patologia : this.patologias) {
            if (patologia.getDescripcionPatologia().trim().equals(name.trim())) {
                return patologia.getIdPatologia();
            }
        }
        return null;
    }

    public void analizeImage(ActionEvent actionEvent) {

        String msg;
        FacesMessage message;
        logger.info("Current imgage for analize : " + this.newImageName);
        if (this.newImageName == null || this.newImageName.equals("gfx/Imagen-animada-Lupa-10.png")) {
            msg = this.diccionario.getString("lbl_crop_image_selected");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, this.diccionario.getString("lbl_crop_image_selected_detail"));
            FacesContext.getCurrentInstance().addMessage(msg, message);
            return;
        }

        imageNetIA = new ImageNetIA();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        try {
            pieResultImage.clear();
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

    public void enviarDiagnostico(ActionEvent actionEvent) {
        validateMessage();
        String msg = "";
        FacesMessage facesMessage;
        String msgToclient = this.message.replace("$PATOLOGIA", getNamePatologia(this.selectedPatologia)).replace("$NOMBRE_SOLICITANTE", this.selectedSolicitud.getTblSolicitante().getNombreSolicitante());
        if (userPushDevice != null) {
            pushServiceBean.doPushNotification(msgToclient, userPushDevice.getDeviceId());
        }
        if (usuarioEmail != null) {
            this.sendMailSSL.sendEmail(this.usuarioEmail.getContacto(), this.diccionario.getString("lbl_result_request") + this.selectedSolicitud.getSerial() + "", msgToclient);
        }
        if (userPushDevice != null || usuarioEmail != null) {
            msg = this.diccionario.getString("lbl_result_request_send_successfully") + this.selectedSolicitud.getTblSolicitante().getNombreSolicitante();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = this.diccionario.getString("lbl_result_request_contact_directly");
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
        }

        FacesContext.getCurrentInstance().addMessage(msg, facesMessage);
    }

    public void guardarRespuesta(ActionEvent actionEvent) {
        String msg, detail = null;
        FacesMessage message;
        validateMessage();
        if (tblDiagnosticoById == null) {
            msg = this.diccionario.getString("wrn_not_save_diagnostic_yet");
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, this.diccionario.getString("wrn_not_save_diagnostic_yet_detail"));
            FacesContext.getCurrentInstance().addMessage(msg, message);
            return;
        }
        this.tblDiagnosticoById.setDescripcionDiagnostico(this.message);
        this.tblDiagnosticoById.setIdPatologia(selectedPatologia);
        this.diagnosticoDao.updateDiagnosticoGeneral(tblDiagnosticoById);
        if (this.respuestaSolicitud == null) {
            respuestaSolicitud = new TblRespuestaSolicitud();
            respuestaSolicitud.setTblDiagnostico(this.tblDiagnosticoById);
            respuestaSolicitud.setTblSolicitud(selectedSolicitud);
            respuestaSolicitud.setDescripcionRespuesta(this.message);
            respuestaSolicitud.setFechaRespuesta(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
            this.diagnosticoDao.createRespuestaSolicitud(respuestaSolicitud);
        } else {
            respuestaSolicitud.setTblDiagnostico(this.tblDiagnosticoById);
            respuestaSolicitud.setTblSolicitud(selectedSolicitud);
            respuestaSolicitud.setDescripcionRespuesta(this.message);
            respuestaSolicitud.setFechaRespuesta(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
            this.diagnosticoDao.updateResRespuestaSolicitud(respuestaSolicitud);
        }

        this.selectedSolicitud.setTblEstado(estadoDao.findEstadoByName("CERRADO"));
        solicitudDao.updateSolicitud(selectedSolicitud);

        msg = this.diccionario.getString("lbl_response_generated_successfully");

        if (this.userPushDevice == null && this.usuarioEmail == null) {
            detail = this.diccionario.getString("lbl_response_generated_successfully_detail");
        }
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, detail);
        FacesContext.getCurrentInstance().addMessage(msg, message);
        this.isSavingResponse = false;
    }

    private String getNamePatologia(Long id) {
        for (TblPatologia patologia : this.patologias) {
            if (patologia.getIdPatologia().equals(id)) {
                return patologia.getDescripcionPatologia();
            }
        }
        return "$PATOLOGIA";
    }

    private void validateMessage() {
        String msg;
        FacesMessage message;
        if (!this.message.contains("$PATOLOGIA")) {
            msg = this.diccionario.getString("wrn_pataology_paramter_required");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(msg, message);
        }

        if (!this.message.contains("$NOMBRE_SOLICITANTE")) {
            msg = this.diccionario.getString("wrn_user_name_paramter_required");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(msg, message);
        }
    }
}
