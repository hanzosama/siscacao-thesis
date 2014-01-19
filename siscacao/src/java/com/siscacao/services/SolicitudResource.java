package com.siscacao.services;
import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitanteDao;
import com.siscacao.dao.SolicitanteDaoImpl;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.model.TblSolicitante;
import com.siscacao.model.TblSolicitud;
import com.siscacao.objects.json.SolicitudJson;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Jorge
 */
@Path("solicitud")
@RequestScoped
public class SolicitudResource {
    
    private TblSolicitud solicitud;
    private TblSolicitante solicitante;
    private SolicitudDao solicitudDao;
    private SolicitanteDao solicitanteDao;
    

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioResource
     */
    public SolicitudResource() {
        this.solicitanteDao = new SolicitanteDaoImpl();
        this.solicitudDao = new SolicitudDaoImpl();
    }

    /**
     * Retrieves representation of an instance of com.siscacao.services.UsuarioResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("registro")
    @Consumes("application/json")
    @Produces("text/html")
    public String getSolicitud(final SolicitudJson solicitudJson ) {
        String result="-1";
       if(!solicitudJson.numeroSolictud.isEmpty()){
         this.solicitud.setSerial(solicitudJson.numeroSolictud);
         this.solicitud= solicitudDao.findSolicitudBySerial(solicitud);
         return this.solicitud.getIdSolicitud().toString();
       }else if(!solicitudJson.numeroDocumento.isEmpty()){           
          this.solicitante.setNumeroDocumento(solicitudJson.numeroDocumento);
          this.solicitante= solicitanteDao.findSolicitanteByNumeroDeDocumento(solicitante);
          this.solicitud=solicitudDao.findSolicitudByIdSolicitante(solicitante);
          return this.solicitud.getIdSolicitud().toString();        
       }
       return result;
    }
  
}