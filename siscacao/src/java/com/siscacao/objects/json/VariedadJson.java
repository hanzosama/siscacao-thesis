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
public class VariedadJson {
    @XmlElement public String id_variedad;
    @XmlElement public String nombre_variedad; 

    public String getId_variedad() {
        return id_variedad;
    }

    public void setId_variedad(String id_variedad) {
        this.id_variedad = id_variedad;
    }

    public String getNombre_variedad() {
        return nombre_variedad;
    }

    public void setNombre_variedad(String nombre_variedad) {
        this.nombre_variedad = nombre_variedad;
    }

}
