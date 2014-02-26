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
public class DepartamentoJson {
    @XmlElement public String nombre_departamento;
    @XmlElement public String id_departamento; 

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }

    public String getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(String id_departamento) {
        this.id_departamento = id_departamento;
    }
    
    
}
