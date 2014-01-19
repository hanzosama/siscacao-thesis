/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblAsignacionSolicitud;
import com.siscacao.model.TblSolicitud;
import com.siscacao.model.TblUsuario;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hanzo
 */
public class SolicitudDaoImpl implements SolicitudDao {

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

    @Override
    public boolean updateSolicitud(TblSolicitud tblSolicitud) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(tblSolicitud);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public List<TblSolicitud> retrieveListSolicitudPending() {
        List<TblSolicitud> listUsersModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "select * from tbl_solicitud solicitud \n"
                + "left join tbl_asignacion_solicitud asignacion on asignacion.id_solicitud=solicitud.id_solicitud\n"
                + "left join tbl_estado estado on estado.id_estado= solicitud.id_estado\n"
                + "where solicitud.id_estado=1 and asignacion.id_usuario is null";
        try {
            session.beginTransaction();
            listUsersModel = (List<TblSolicitud>) session.createSQLQuery(sql).addEntity("solicitud", TblSolicitud.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listUsersModel;
    }

    @Override
    public void signedSolicitud(TblSolicitud tblSolicitud, TblUsuario tblUsuario) {
        TblAsignacionSolicitud asigacion = new TblAsignacionSolicitud();
        asigacion.setTblUsuario(tblUsuario);
        asigacion.setTblSolicitud(tblSolicitud);
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
         try {
            session.beginTransaction();
            session.save(asigacion);
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }               
    }
    
    
}
