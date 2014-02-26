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
public class TipoDocumentoJson {
    @XmlElement public String id_tipo_documento;
    @XmlElement public String descripcion_tipo; 

    public String getId_tipo_documento() {
        return id_tipo_documento;
    }

    public void setId_tipo_documento(String id_tipo_documento) {
        this.id_tipo_documento = id_tipo_documento;
    }

    public String getdescripcion_tipo() {
        return descripcion_tipo;
    }

    public void setdescripcion_tipo(String descripcion_tipo) {
        this.descripcion_tipo = descripcion_tipo;
    }   
    
}
