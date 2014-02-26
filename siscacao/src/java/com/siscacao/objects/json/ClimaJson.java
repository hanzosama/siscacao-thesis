/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.objects.json;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Daniel
 */
public class ClimaJson {
    @XmlElement public String id_clima;
    @XmlElement public String nombre_clima; 

    public String getId_clima() {
        return id_clima;
    }

    public void setId_clima(String id_clima) {
        this.id_clima = id_clima;
    }

    public String getNombre_clima() {
        return nombre_clima;
    }

    public void setNombre_clima(String nombre_clima) {
        this.nombre_clima = nombre_clima;
    }
    
}
