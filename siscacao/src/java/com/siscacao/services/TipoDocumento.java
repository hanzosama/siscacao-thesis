/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.services;

import com.siscacao.dao.TipoDocumentoDao;
import com.siscacao.dao.TipoDocumentoDaoImpl;
import com.siscacao.model.TblTipoDocumento;
import com.siscacao.objects.json.TipoDocumentoJson;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Daniel
 */

@Path("tipodocumento")
@RequestScoped
public class TipoDocumento {
    
    private List<TblTipoDocumento> tipoDocumento;
    private TipoDocumentoDao tipoDocumentoDao;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioResource
     */
    public TipoDocumento() {
        this.tipoDocumentoDao = new TipoDocumentoDaoImpl();
    }
    
    @GET
    @Path("registro")    
    @Produces("application/json")
    public List<TipoDocumentoJson>   BuscarTipoDocumento() {
        List<TipoDocumentoJson> depList = new ArrayList<TipoDocumentoJson>();
        this.tipoDocumento= tipoDocumentoDao.findAllTypeDocument();
        for(TblTipoDocumento tipo:this.tipoDocumento){
            TipoDocumentoJson tipodoc = new TipoDocumentoJson();
            tipodoc.setId_tipo_documento(String.valueOf(tipo.getIdTipoDocumento()));
            tipodoc.setdescripcion_tipo(tipo.getDescripcionTipo());
        depList.add(tipodoc);
        }
        return depList;
    }
}
