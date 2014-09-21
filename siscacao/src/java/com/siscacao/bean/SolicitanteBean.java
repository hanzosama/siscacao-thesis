/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.ClimaDao;
import com.siscacao.dao.ClimaDaoImpl;
import com.siscacao.dao.ContactoDao;
import com.siscacao.dao.ContactoDaoImpl;
import com.siscacao.dao.CultivoDao;
import com.siscacao.dao.CultivoDaoImpl;
import com.siscacao.dao.DepartamentoDao;
import com.siscacao.dao.DepartamentoDaoImpl;
import com.siscacao.dao.EstadoDao;
import com.siscacao.dao.EstadoDaoImpl;
import com.siscacao.dao.EstadoProduccionDao;
import com.siscacao.dao.EstadoProduccionDaoImpl;
import com.siscacao.dao.ImagenDao;
import com.siscacao.dao.ImagenDaoImpl;
import com.siscacao.dao.SolicitanteDao;
import com.siscacao.dao.SolicitanteDaoImpl;
import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.dao.TecnicaDao;
import com.siscacao.dao.TecnicaDaoImpl;
import com.siscacao.dao.TipoContactoDao;
import com.siscacao.dao.TipoDocumentoDao;
import com.siscacao.dao.TipoDocumentoDaoImpl;
import com.siscacao.dao.VariedadDao;
import com.siscacao.dao.VariedadDaoImpl;
import com.siscacao.model.TblAsignacionSolicitud;
import com.siscacao.model.TblClima;
import com.siscacao.model.TblContacto;
import com.siscacao.model.TblCultivo;
import com.siscacao.model.TblDepartamento;
import com.siscacao.model.TblEstado;
import com.siscacao.model.TblEstadoProduccion;
import com.siscacao.model.TblImagen;
import com.siscacao.model.TblSolicitante;
import com.siscacao.model.TblSolicitud;
import com.siscacao.model.TblTecnicaCultivo;
import com.siscacao.model.TblTipoDocumento;
import com.siscacao.model.TblVariedad;
import com.siscacao.util.FileUploadController;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@SessionScoped
public class SolicitanteBean implements Serializable {

    @EJB
    private TblSolicitante solicitante;
    private SolicitanteDao solicitanteDao;
    private AppBean baseURL;
    private Long selectedDepartamento;
    private List<TblDepartamento> listDepartamentos;
    private DepartamentoDao departamentoDao;
    private Long selectedTipoDocumento;
    private List<TblTipoDocumento> listTblTipoDocumentos;
    private TipoDocumentoDao tipoDocumentoDao;
    private String telefonoFijo;
    private String telefonoMovil;
    private String email;
    private String folderName = "";
    private Long selectedClima;
    private List<TblClima> listClima;
    private ClimaDao climaDao;
    private Long selectedVariedad;
    private List<TblVariedad> listVariedad;
    private VariedadDao variedadDao;
    private Long selectedTecnica;
    private List<TblTecnicaCultivo> listTecnica;
    private TecnicaDao tecnicaDao;
    private Long selectedEstadoPro;
    private List<TblEstadoProduccion> listEstadoPro;
    private EstadoProduccionDao estadoProduccionDao;
    private FileUploadController fileUploadController;
    private ContactoDao contactoDao;
    private TipoContactoDao tipoContactoDao;
    private TblCultivo cultivo;
    private CultivoDao cultivoDao;
    private SolicitudDao solicitudDao;
    private TblSolicitud tblSolicitud;
    private ImagenDao imagenDao;
    private String serial;
    private String descripcion;
    private EstadoDao estadoDao;

    public SolicitanteBean() {
        this.baseURL = new AppBean();
        this.solicitanteDao = new SolicitanteDaoImpl();
        this.departamentoDao = new DepartamentoDaoImpl();
        this.tipoDocumentoDao = new TipoDocumentoDaoImpl();
        this.climaDao = new ClimaDaoImpl();
        this.variedadDao = new VariedadDaoImpl();
        this.tecnicaDao = new TecnicaDaoImpl();
        this.estadoProduccionDao = new EstadoProduccionDaoImpl();
        this.contactoDao = new ContactoDaoImpl();
        this.tipoContactoDao = new TipoContactoDaoImpl();
        this.fileUploadController = new FileUploadController();
        this.cultivoDao = new CultivoDaoImpl();
        this.solicitudDao = new SolicitudDaoImpl();
        this.imagenDao = new ImagenDaoImpl();
        this.estadoDao = new EstadoDaoImpl();

        if (this.solicitante == null) {
            this.solicitante = new TblSolicitante();
        }
        if (this.cultivo == null) {
            this.cultivo = new TblCultivo();
        }

        this.listDepartamentos = new ArrayList<TblDepartamento>(departamentoDao.findAllDepartamento());
        this.listTblTipoDocumentos = new ArrayList<TblTipoDocumento>(tipoDocumentoDao.findAllTypeDocument());
        this.listClima = new ArrayList<TblClima>(climaDao.findAllClimas());
        this.listVariedad = new ArrayList<TblVariedad>(variedadDao.findAllVariedad());
        this.listTecnica = new ArrayList<TblTecnicaCultivo>(tecnicaDao.findAllTecnicaCultivo());
        this.listEstadoPro = new ArrayList<TblEstadoProduccion>(estadoProduccionDao.findAllEstadoProduccionDao());
    }

    public TblSolicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(TblSolicitante solicitante) {
        this.solicitante = solicitante;
    }

    public Long getSelectedDepartamento() {
        return selectedDepartamento;

    }

    public void setSelectedDepartamento(Long selectedDepartamento) {
        this.selectedDepartamento = selectedDepartamento;
    }

    public List<TblDepartamento> getListDepartamentos() {
        return listDepartamentos;
    }

    public void setListDepartamentos(List<TblDepartamento> listDepartamentos) {
        this.listDepartamentos = listDepartamentos;
    }

    public List<TblTipoDocumento> getListTblTipoDocumentos() {
        return listTblTipoDocumentos;
    }

    public Long getSelectedTipoDocumento() {
        return selectedTipoDocumento;
    }

    public void setSelectedTipoDocumento(Long selectedTipoDocumento) {
        this.selectedTipoDocumento = selectedTipoDocumento;
    }

    public void setListTblTipoDocumentos(List<TblTipoDocumento> listTblTipoDocumentos) {
        this.listTblTipoDocumentos = listTblTipoDocumentos;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public Long getSelectedClima() {
        return selectedClima;
    }

    public void setSelectedClima(Long selectedClima) {
        this.selectedClima = selectedClima;
    }

    public List<TblClima> getListClima() {
        return listClima;
    }

    public void setListClima(List<TblClima> listClima) {
        this.listClima = listClima;
    }

    public TblCultivo getCultivo() {
        return cultivo;
    }

    public void setCultivo(TblCultivo cultivo) {
        this.cultivo = cultivo;
    }

    public Long getSelectedVariedad() {
        return selectedVariedad;
    }

    public void setSelectedVariedad(Long selectedVariedad) {
        this.selectedVariedad = selectedVariedad;
    }

    public List<TblVariedad> getListVariedad() {
        return listVariedad;
    }

    public void setListVariedad(List<TblVariedad> listVariedad) {
        this.listVariedad = listVariedad;
    }

    public Long getSelectedTecnica() {
        return selectedTecnica;
    }

    public void setSelectedTecnica(Long selectedTecnica) {
        this.selectedTecnica = selectedTecnica;
    }

    public List<TblTecnicaCultivo> getListTecnica() {
        return listTecnica;
    }

    public void setListTecnica(List<TblTecnicaCultivo> listTecnica) {
        this.listTecnica = listTecnica;
    }

    public Long getSelectedEstadoPro() {
        return selectedEstadoPro;
    }

    public void setSelectedEstadoPro(Long selectedEstadoPro) {
        this.selectedEstadoPro = selectedEstadoPro;
    }

    public List<TblEstadoProduccion> getListEstadoPro() {
        return listEstadoPro;
    }

    public void setListEstadoPro(List<TblEstadoProduccion> listEstadoPro) {
        this.listEstadoPro = listEstadoPro;
    }
    public String getSerial() {
        return serial;
    }
    
    public void saveInfoSolicitante(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        //Guarda Solicitante
        TblContacto contactoFijo = new TblContacto();
        contactoFijo.setContacto(telefonoFijo);
        contactoFijo.setTblTipoContacto(tipoContactoDao.findTipoContactoId("TF"));

        TblContacto contactoMovil = new TblContacto();
        contactoMovil.setContacto(telefonoMovil);
        contactoMovil.setTblTipoContacto(tipoContactoDao.findTipoContactoId("TM"));
        
        TblContacto contactoEmail = new TblContacto();
        contactoEmail.setContacto(email);
        contactoEmail.setTblTipoContacto(tipoContactoDao.findTipoContactoId("EM"));

        this.solicitante.setIdDepartamento(this.selectedDepartamento);
        for (TblTipoDocumento tpdoc : this.listTblTipoDocumentos) {
            System.out.println("select tipo doc: " + this.selectedTipoDocumento + " DB" + tpdoc.getIdTipoDocumento());

            if (tpdoc.getIdTipoDocumento().equals(this.selectedTipoDocumento)) {
                System.out.println("tipo doc:" + tpdoc.getNombreTipo());
                this.solicitante.setTblTipoDocumento(tpdoc);
            }
        }
        solicitanteDao.CreateSolicitante(solicitante);

        contactoFijo.setTblSolicitante(solicitante);
        contactoMovil.setTblSolicitante(solicitante);
        contactoEmail.setTblSolicitante(solicitante);

        contactoDao.createContacto(contactoMovil);
        contactoDao.createContacto(contactoFijo);
        contactoDao.createContacto(contactoEmail);

        //Guarda Información del cultivo

        saveCultivoSolicitante();

        //Guarda  imagenes del usuario en path de applicacion
        uploadApplicationUserFolder();

        //registrar solicitud

        createSolicitud();
        //registra imagenes url para el usuario
        createImagesSolicitud();
        context.addCallbackParam("serial", this.serial);

    }

    public void uploadToTmpUserFolder(FileUploadEvent event) {
        folderName = this.solicitante.getNombreSolicitante().toLowerCase().trim().replace(" ", "") + this.solicitante.getNumeroDocumento().toLowerCase().trim().replace(" ", "");
        fileUploadController.uploadToTmpUserFolder(event, folderName);
    }

    public void loginSolicitante(ActionEvent actionEvent) {
        FacesMessage msg;
        boolean loggedIn;
        String redirect = "";
        RequestContext context = RequestContext.getCurrentInstance();
        this.solicitante = this.solicitanteDao.findSolicitanteByNumeroDeDocumento(this.solicitante);
        if (this.solicitante != null) {
            loggedIn = true;
            //  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.usuario.getCuenta());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido ", this.solicitante.getNombreSolicitante());
            FacesContext.getCurrentInstance().getViewRoot().setViewId("");
            redirect = "view/inicio.jsf?faces-redirect=true";
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usted no se encuentra registrado", "verifique su numero de identificacion e intente nuevamente");
            if (this.solicitante == null) {
                this.solicitante = new TblSolicitante();
            }
        }
        context.addCallbackParam("loggedIn", loggedIn);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void saveCultivoSolicitante() {
        cultivo.setIdClima(selectedClima);

        for (TblVariedad variedad : listVariedad) {
            if (variedad.getIdVariedad().equals(selectedVariedad)) {
                cultivo.setTblVariedad(variedad);
            }
        }
        for (TblTecnicaCultivo tecnica : listTecnica) {
            if (tecnica.getIdTecnica().equals(selectedTecnica)) {
                cultivo.setTblTecnicaCultivo(tecnica);
            }
        }
        for (TblEstadoProduccion estadoPro : listEstadoPro) {
            if (estadoPro.getIdEstadoProduccion().equals(selectedEstadoPro)) {
                cultivo.setTblEstadoProduccion(estadoPro);
            }
        }

        cultivoDao.createCultivo(cultivo);
    }

    private void uploadApplicationUserFolder() {
        if (!folderName.equals("") && folderName != null) {
            fileUploadController.uploadToApplicationUserFolder(folderName);
        }
    }

    public void createSolicitud() {
        Date currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        TblEstado estado = this.estadoDao.findEstadoById(Long.valueOf(1));
        this.tblSolicitud = new TblSolicitud();
        this.tblSolicitud.setFechaSolicitud(currentDate);
        this.tblSolicitud.setTblSolicitante(solicitante);
        this.tblSolicitud.setTblCultivo(cultivo);
        this.tblSolicitud.setTblEstado(estado);
        this.tblSolicitud.setDescripcion(descripcion);
        serial = currentDate.toString();
        serial = serial.replace("-", "");
        serial = serial.replace(":", "");
        serial = serial.replace(" ", "");
        serial = serial.substring(0, 12);
        solicitudDao.createSolicitud(tblSolicitud);
        this.serial = serial + tblSolicitud.getIdSolicitud().toString();
        this.tblSolicitud.setSerial(serial);
        solicitudDao.updateSolicitud(tblSolicitud);
    }

    public void createImagesSolicitud() {
        if (!folderName.equals("") && folderName != null) {
            ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
            File f = new File(extContext.getRealPath("resources/" + "/user/" + folderName));
            if (f.exists()) {
                // Recuperamos la lista de ficheros
                String filePath;
                File[] ficheros = f.listFiles();
                for (int x = 0; x < ficheros.length; x++) {
                    filePath = folderName + "/" + ficheros[x].getName();
                    TblImagen imagen = new TblImagen();
                    imagen.setPathImagen(filePath);
                    imagen.setNombreImagen(ficheros[x].getName());
                    imagen.setTblSolicitud(tblSolicitud);
                    imagenDao.createImagen(imagen);
                }
            } else {
                System.out.println("No existe ese directorio");
            }
        }
    }

    public String closeRegistro() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getViewRoot().setViewId("");
        return "portal/index.jsf?faces-redirect=true";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    

}
