/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.services;

import com.siscacao.dao.ClimaDao;
import com.siscacao.dao.ClimaDaoImpl;
import com.siscacao.model.TblClima;
import com.siscacao.objects.json.ClimaJson;
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
@Path("clima")
@RequestScoped
public class ClimaResources {
    
    
   private List<TblClima> climas;
    private ClimaDao climaDao;

    public ClimaResources() {
        this.climaDao = new ClimaDaoImpl();
    }
    
    @GET
    @Path("registro")    
    @Produces("application/json")
    public List<ClimaJson>   BuscarClimas() {
        List<ClimaJson> climaList = new ArrayList<ClimaJson>();
        this.climas= climaDao.findAllClimas();
        for(TblClima clima:this.climas){
            ClimaJson objClima = new ClimaJson();
            objClima.setId_clima(String.valueOf(clima.getIdClima()));
            objClima.setNombre_clima(clima.getNombreClima());
        climaList.add(objClima);
        }
        return climaList;
    }
}
