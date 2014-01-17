package com.siscacao.model;
// Generated 20/10/2013 04:28:11 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * TblPatologia generated by hbm2java
 */
public class TblPatologia  implements java.io.Serializable {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Basic(optional = false)
     @Column(name = "id_patologia") 
     private Long idPatologia;
     private String descripcionPatologia;
     private String observacionesPatologia;
     private Set tblSolicituds = new HashSet(0);
     private Set tblSintomaPatologias = new HashSet(0);

    public TblPatologia() {
    }

	
    public TblPatologia(Long idPatologia) {
        this.idPatologia = idPatologia;
    }
    public TblPatologia(Long idPatologia, String descripcionPatologia, String observacionesPatologia, Set tblSolicituds, Set tblSintomaPatologias) {
       this.idPatologia = idPatologia;
       this.descripcionPatologia = descripcionPatologia;
       this.observacionesPatologia = observacionesPatologia;
       this.tblSolicituds = tblSolicituds;
       this.tblSintomaPatologias = tblSintomaPatologias;
    }
   
    public Long getIdPatologia() {
        return this.idPatologia;
    }
    
    public void setIdPatologia(Long idPatologia) {
        this.idPatologia = idPatologia;
    }
    public String getDescripcionPatologia() {
        return this.descripcionPatologia;
    }
    
    public void setDescripcionPatologia(String descripcionPatologia) {
        this.descripcionPatologia = descripcionPatologia;
    }
    public String getObservacionesPatologia() {
        return this.observacionesPatologia;
    }
    
    public void setObservacionesPatologia(String observacionesPatologia) {
        this.observacionesPatologia = observacionesPatologia;
    }
    public Set getTblSolicituds() {
        return this.tblSolicituds;
    }
    
    public void setTblSolicituds(Set tblSolicituds) {
        this.tblSolicituds = tblSolicituds;
    }
    public Set getTblSintomaPatologias() {
        return this.tblSintomaPatologias;
    }
    
    public void setTblSintomaPatologias(Set tblSintomaPatologias) {
        this.tblSintomaPatologias = tblSintomaPatologias;
    }




}


