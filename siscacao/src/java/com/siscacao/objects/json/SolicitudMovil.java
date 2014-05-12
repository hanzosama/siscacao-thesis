/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.objects.json;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Hanzo
 */
public class SolicitudMovil {
    @XmlElement public String nombreSolicitante;
    @XmlElement public String tipoDocumento; 
    @XmlElement public String numeroDocumento; 
    @XmlElement public String numeroCelular; 
    @XmlElement public String telefonoFijo; 
    @XmlElement public String idDepartamento; 
    @XmlElement public String nombreVereda; 
    @XmlElement public String nombreCultivo; 
    @XmlElement public String extensionCultivo; 
    @XmlElement public String idClima; 
    @XmlElement public String idVariedad; 
    @XmlElement public String idEstadoCultivo;
    @XmlElement public String imagenSolicitud;    
}
