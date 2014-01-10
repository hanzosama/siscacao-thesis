/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblSolicitud;
import com.siscacao.util.HibernateConnectUtil;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class SolicitudDaoImpl implements SolicitudDao{

    @Override
    public boolean createSolicitud(TblSolicitud tblSolicitud) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(tblSolicitud);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }

}
