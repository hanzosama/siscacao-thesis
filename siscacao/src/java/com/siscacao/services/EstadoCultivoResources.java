/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.services;

import com.siscacao.dao.EstadoProduccionDao;
import com.siscacao.dao.EstadoProduccionDaoImpl;
import com.siscacao.model.TblEstadoProduccion;
import com.siscacao.objects.json.EstadoCultivoJson;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Daniel
 */
@Path("estadocultivo")
@RequestScoped
public class EstadoCultivoResources {
 
    
    private List<TblEstadoProduccion> estadosProduccion;
    private EstadoProduccionDao estadoProduccionDao;

    public EstadoCultivoResources() {
        this.estadoProduccionDao = new EstadoProduccionDaoImpl();
    }
    
      @GET
    @Path("registro")    
    @Produces("application/json")
    public List<EstadoCultivoJson>   BuscarClimas() {
        List<EstadoCultivoJson> estadoCultivoList = new ArrayList<EstadoCultivoJson>();
        this.estadosProduccion= estadoProduccionDao.findAllEstadoProduccionDao();
        for(TblEstadoProduccion estado:this.estadosProduccion){
            EstadoCultivoJson objEstado = new EstadoCultivoJson();
            objEstado.setId_estado(String.valueOf(estado.getIdEstadoProduccion()));
            objEstado.setDescripcion_estado(estado.getDescripcionEstado());
        estadoCultivoList.add(objEstado);
        }
        return estadoCultivoList;
    }  
    
    
}
