package com.siscacao.services;

import com.siscacao.bean.AppBean;
import com.siscacao.bean.TipoContactoDaoImpl;
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
import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitanteDao;
import com.siscacao.dao.SolicitanteDaoImpl;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.dao.TecnicaDao;
import com.siscacao.dao.TecnicaDaoImpl;
import com.siscacao.dao.TipoContactoDao;
import com.siscacao.dao.TipoDocumentoDao;
import com.siscacao.dao.TipoDocumentoDaoImpl;
import com.siscacao.dao.VariedadDao;
import com.siscacao.dao.VariedadDaoImpl;
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
import com.siscacao.objects.json.SolicitudJson;
import com.siscacao.objects.json.SolicitudMovil;
import com.siscacao.util.FileUploadController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * REST Web Service
 *
 * @author Jorge
 */
@Path("solicitud")
@RequestScoped
public class SolicitudResource {

    @Context
    ServletContext context;
    private TblSolicitud solicitud;
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
    private EstadoDao estadoDao;

    /**
     * Creates a new instance of UsuarioResource
     */
    public SolicitudResource() {
        this.solicitanteDao = new SolicitanteDaoImpl();
        this.solicitudDao = new SolicitudDaoImpl();
        this.departamentoDao = new DepartamentoDaoImpl();
        this.tipoDocumentoDao = new TipoDocumentoDaoImpl();
        this.climaDao = new ClimaDaoImpl();
        this.variedadDao = new VariedadDaoImpl();
        this.tecnicaDao = new TecnicaDaoImpl();
        this.estadoProduccionDao = new EstadoProduccionDaoImpl();
        this.contactoDao = new ContactoDaoImpl();
        this.tipoContactoDao = new TipoContactoDaoImpl();
        this.cultivoDao = new CultivoDaoImpl();
        this.imagenDao = new ImagenDaoImpl();
        this.estadoDao = new EstadoDaoImpl();

        this.listDepartamentos = new ArrayList<TblDepartamento>(departamentoDao.findAllDepartamento());
        this.listTblTipoDocumentos = new ArrayList<TblTipoDocumento>(tipoDocumentoDao.findAllTypeDocument());
        this.listClima = new ArrayList<TblClima>(climaDao.findAllClimas());
        this.listVariedad = new ArrayList<TblVariedad>(variedadDao.findAllVariedad());
        this.listTecnica = new ArrayList<TblTecnicaCultivo>(tecnicaDao.findAllTecnicaCultivo());
        this.listEstadoPro = new ArrayList<TblEstadoProduccion>(estadoProduccionDao.findAllEstadoProduccionDao());

    }

    /**
     * Retrieves representation of an instance of
     * com.siscacao.services.UsuarioResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("registro")
    @Consumes("application/json")
    @Produces("text/html")
    public String getSolicitud(final SolicitudJson solicitudJson) {
        String result = "-1";
        this.solicitante = new TblSolicitante();
        this.solicitud = new TblSolicitud();
        if (!solicitudJson.numeroSolicitud.isEmpty()) {
            this.solicitud.setSerial(solicitudJson.numeroSolicitud);
            this.solicitud = solicitudDao.findSolicitudBySerial(solicitud);
            if (this.solicitud != null && !this.solicitud.getIdSolicitud().toString().isEmpty()) {
                return this.solicitud.getIdSolicitud().toString();
            }
        } else if (!solicitudJson.numeroDocumento.isEmpty()) {
            this.solicitante.setNumeroDocumento(solicitudJson.numeroDocumento);
            this.solicitante = solicitanteDao.findSolicitanteByNumeroDeDocumento(solicitante);
            if (this.solicitante != null) {
                this.solicitud = solicitudDao.findSolicitudByIdSolicitante(solicitante);
            }
            if (this.solicitud != null && solicitud.getIdSolicitud() != null && !this.solicitud.getIdSolicitud().toString().isEmpty()) {
                return this.solicitud.getIdSolicitud().toString();
            }
        }
        return result;
    }

    @POST
    @Path("registrar_solicitud")
    @Consumes("application/json")
    @Produces("text/html")
    public String resgistrarSolicitud(final SolicitudMovil solicitudMovil) {
        String result = "1";
        System.out.println(solicitudMovil.imagenSolicitud);
        System.out.println(solicitudMovil.nombreSolicitante);
        this.solicitante = new TblSolicitante();
        this.solicitante.setNombreSolicitante(solicitudMovil.nombreSolicitante);
        this.solicitante.setNumeroDocumento(solicitudMovil.numeroDocumento);
        TblContacto contactoFijo = new TblContacto();
        contactoFijo.setContacto(solicitudMovil.telefonoFijo);
        contactoFijo.setTblTipoContacto(tipoContactoDao.findTipoContactoId("TF"));

        TblContacto contactoMovil = new TblContacto();
        contactoMovil.setContacto(solicitudMovil.numeroCelular);
        contactoMovil.setTblTipoContacto(tipoContactoDao.findTipoContactoId("TM"));

        this.solicitante.setIdDepartamento(Long.valueOf(solicitudMovil.idDepartamento));
        for (TblTipoDocumento tpdoc : this.listTblTipoDocumentos) {

            if (tpdoc.getIdTipoDocumento().equals(Long.valueOf(solicitudMovil.tipoDocumento))) {

                this.solicitante.setTblTipoDocumento(tpdoc);
            }
        }
        solicitanteDao.CreateSolicitante(solicitante);

        contactoFijo.setTblSolicitante(solicitante);
        contactoMovil.setTblSolicitante(solicitante);

        contactoDao.createContacto(contactoMovil);
        contactoDao.createContacto(contactoFijo);

        saveCultivoSolicitante(solicitudMovil);


        folderName = this.solicitante.getNombreSolicitante().toLowerCase().trim().replace(" ", "") + this.solicitante.getNumeroDocumento().toLowerCase().trim().replace(" ", "");


        saveImage(solicitudMovil);
        createSolicitud(solicitudMovil);
        createImagesSolicitud();
        return serial;
    }

    private void saveCultivoSolicitante(final SolicitudMovil solicitudMovil) {
        cultivo = new TblCultivo();
        cultivo.setNombreCultivo(solicitudMovil.nombreCultivo);
        cultivo.setExtensionCultivo(solicitudMovil.extensionCultivo);
        cultivo.setIdClima(Long.valueOf(solicitudMovil.idClima));

        for (TblVariedad variedad : listVariedad) {
            if (variedad.getIdVariedad().equals(Long.valueOf(solicitudMovil.idVariedad))) {
                cultivo.setTblVariedad(variedad);
            }
        }

        for (TblEstadoProduccion estadoPro : listEstadoPro) {
            if (estadoPro.getIdEstadoProduccion().equals(Long.valueOf(solicitudMovil.idEstadoCultivo))) {
                cultivo.setTblEstadoProduccion(estadoPro);
            }
        }

        cultivoDao.createCultivo(cultivo);
    }

    private void saveImage(SolicitudMovil solicitudMovil) {

        File folder = new File(context.getRealPath("resources/" + "/user/" + folderName));
        System.out.println("FOLDER PATH: " + folder.getAbsolutePath());
        folder.mkdir();
        try {
            byte[] imageByteArray = decodeImage(solicitudMovil.imagenSolicitud);

            // Write a image byte array into file system
            FileOutputStream imageOutFile = new FileOutputStream(folder.getAbsoluteFile() + "/tmpimage.jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    public void createSolicitud(SolicitudMovil solicitudMovil) {
        Date currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        TblEstado estado = this.estadoDao.findEstadoById(Long.valueOf(1));
        this.tblSolicitud = new TblSolicitud();
        this.tblSolicitud.setFechaSolicitud(currentDate);
        this.tblSolicitud.setTblSolicitante(solicitante);
        this.tblSolicitud.setTblCultivo(cultivo);
        this.tblSolicitud.setTblEstado(estado);
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
            File f = new File(context.getRealPath("resources/" + "/user/" + folderName));
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
}
