/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.objects.json;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hanzo
 */
@XmlRootElement
public class UsuarioJson {
  @XmlElement public String cuenta;
  @XmlElement public String pass;  
}
