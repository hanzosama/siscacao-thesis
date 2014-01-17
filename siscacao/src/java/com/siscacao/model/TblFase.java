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
 * TblFase generated by hbm2java
 */
public class TblFase  implements java.io.Serializable {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Basic(optional = false)
     @Column(name = "id_fase") 
     private Long idFase;
     private String descripcionFase;
     private Set tblSolicituds = new HashSet(0);

    public TblFase() {
    }

	
    public TblFase(Long idFase) {
        this.idFase = idFase;
    }
    public TblFase(Long idFase, String descripcionFase, Set tblSolicituds) {
       this.idFase = idFase;
       this.descripcionFase = descripcionFase;
       this.tblSolicituds = tblSolicituds;
    }
   
    public Long getIdFase() {
        return this.idFase;
    }
    
    public void setIdFase(Long idFase) {
        this.idFase = idFase;
    }
    public String getDescripcionFase() {
        return this.descripcionFase;
    }
    
    public void setDescripcionFase(String descripcionFase) {
        this.descripcionFase = descripcionFase;
    }
    public Set getTblSolicituds() {
        return this.tblSolicituds;
    }
    
    public void setTblSolicituds(Set tblSolicituds) {
        this.tblSolicituds = tblSolicituds;
    }




}


