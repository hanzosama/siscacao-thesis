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
public class pushJson {
    @XmlElement public String numeroDocumento; 
    @XmlElement public String pushDeviceId; 

    @Override
    public String toString() {
        return "pushJson{" + "numeroDocumento=" + numeroDocumento + ", pushDeviceId=" + pushDeviceId + '}';
    }        
}
