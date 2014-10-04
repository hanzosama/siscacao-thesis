/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblCultivo;
import com.siscacao.util.HibernateConnectUtil;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hanzo
 */
public class CultivoDaoImpl implements CultivoDao{

    @Override
    public boolean createCultivo(TblCultivo tblCultivo) {
         boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(tblCultivo) ;
            session.beginTransaction().commit();
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
            result=false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public TblCultivo getCultivoById(Long id) {
         TblCultivo cultivo=null;  
     Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblCultivo WHERE idCultivo='" + id + "'";
        try {
            session.beginTransaction();
            cultivo = (TblCultivo) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();            
        }
        return cultivo;   
    }

    @Override
    public TblCultivo getCultivo(TblCultivo cultivo) {
        
         TblCultivo tblCultivo = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            tblCultivo = (TblCultivo)session.createCriteria(TblCultivo.class).add(Restrictions.eq("idCultivo",cultivo.getIdCultivo())).setFetchMode("tblEstadoProduccion", FetchMode.EAGER).setFetchMode("tblVariedad", FetchMode.EAGER).setFetchMode("tblTecnicaCultivo", FetchMode.EAGER).list().get(0);
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return tblCultivo;
    }
    
    
    
    
}
