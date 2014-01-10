/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.services;

import com.siscacao.dao.UsuarioDao;
import com.siscacao.dao.UsuarioDaoImpl;
import com.siscacao.model.TblUsuario;
import com.siscacao.objects.json.UsuarioJson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Hanzo
 */
@Path("usuario")
@RequestScoped
public class UsuarioResource {
    
    private TblUsuario usuario;
    private UsuarioDao usuarioDAO;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioResource
     */
    public UsuarioResource() {
        this.usuarioDAO = new UsuarioDaoImpl();
    }

    /**
     * Retrieves representation of an instance of com.siscacao.services.UsuarioResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("registro")
    @Consumes("application/json")
    @Produces("text/html")
    public String getRegistro(final UsuarioJson usuario ) {
       this.usuario = new TblUsuario();
       this.usuario.setCuenta(usuario.cuenta);
       this.usuario.setContrasena(usuario.pass);
       this.usuario = this.usuarioDAO.login(this.usuario);
        if (this.usuario != null) {
        return "true";
        }else{
        return "false";
        }
    }

    /**
     * PUT method for updating or creating an instance of UsuarioResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
