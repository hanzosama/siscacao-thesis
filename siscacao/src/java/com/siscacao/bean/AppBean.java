
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import com.siscacao.util.classPath;
import java.io.Serializable;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@ApplicationScoped
public class AppBean implements Serializable{

    /**
     * Creates a new instance of AppBean
     */
    public AppBean() {
    }
    
    public String getBaseURL(){
        return classPath.baseURL();
    }
    public String baseURLLogin(){
        return classPath.baseURLLogin();
    }
    public String baseURLUserImage(){
        return classPath.baseURLUserImage();
    }
    
     public String getBasePath(){
        return classPath.basePath();
    }
}
