/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblSolicitante;
import com.siscacao.util.HibernateConnectUtil;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class SolicitanteDaoImpl implements SolicitanteDao {

    @Override
    public TblSolicitante findSolicitanteByNumeroDeDocumento(TblSolicitante solicitante) {
     TblSolicitante solicitanteTmp=null;  
     Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblSolicitante WHERE numeroDocumento='" + solicitante.getNumeroDocumento() + "'";
        try {
            session.beginTransaction();
            solicitanteTmp = (TblSolicitante) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return solicitanteTmp;    
    }

    @Override
    public boolean CreateSolicitante(TblSolicitante solicitante) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(solicitante) ;
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
    public boolean UpdateSolicitante(TblSolicitante solicitante) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(solicitante) ;
            session.beginTransaction().commit();
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
            result=false;
            session.beginTransaction().rollback();
        }
        return result;
    }
    
}
