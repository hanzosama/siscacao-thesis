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
public class SolicitudJson {
    @XmlElement public String numeroSolicitud;
    @XmlElement public String numeroDocumento;  
    @XmlElement public String nombreSolicitante;  
    @XmlElement public String diagnostico;  
    @XmlElement public String recomendaciones;  
    @XmlElement public String estado;  
}
