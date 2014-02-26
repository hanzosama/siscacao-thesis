/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.services;

import com.siscacao.dao.VariedadDao;
import com.siscacao.dao.VariedadDaoImpl;
import com.siscacao.model.TblClima;
import com.siscacao.model.TblVariedad;
import com.siscacao.objects.json.ClimaJson;
import com.siscacao.objects.json.VariedadJson;
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
@Path("variedad")
@RequestScoped
public class VariedadResources {
 
    private List<TblVariedad> variedades;
    private VariedadDao variedadDao;
    
    public VariedadResources() {
        this.variedadDao = new VariedadDaoImpl();
    }
    
     @GET
    @Path("registro")    
    @Produces("application/json")
    public List<VariedadJson>   BuscarClimas() {
        List<VariedadJson> variedadList = new ArrayList<VariedadJson>();
        this.variedades= variedadDao.findAllVariedad();
        for(TblVariedad variedad:this.variedades){
            VariedadJson objVariedad = new VariedadJson();
            objVariedad.setId_variedad(String.valueOf(variedad.getIdVariedad()));
            objVariedad.setNombre_variedad(variedad.getNombreVariedad());
        variedadList.add(objVariedad);
        }
        return variedadList;
    }
}
