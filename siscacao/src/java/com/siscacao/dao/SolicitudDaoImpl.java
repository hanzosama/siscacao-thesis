/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblAsignacionSolicitud;
import com.siscacao.model.TblEstado;
import com.siscacao.model.TblImagen;
import com.siscacao.model.TblSolicitante;
import com.siscacao.model.TblSolicitud;
import com.siscacao.model.TblUsuario;
import com.siscacao.util.HibernateConnectUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public List<TblSolicitud> retrieveListSolicitudPendingForUser(Long id_user) {
        List<TblSolicitud> listUsersModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "select * from tbl_solicitud solicitud \n"
                + "left join tbl_asignacion_solicitud asignacion on asignacion.id_solicitud=solicitud.id_solicitud\n"
                + "left join tbl_estado estado on estado.id_estado= solicitud.id_estado\n"
                + "where solicitud.id_estado=1 and asignacion.id_usuario=" + id_user + "";
        try {
            session.beginTransaction();
            listUsersModel = (List<TblSolicitud>) session.createSQLQuery(sql).addEntity("solicitud", TblSolicitud.class).list();
            for (TblSolicitud sol : listUsersModel) {
                Set<TblImagen> tblImagens = new HashSet<TblImagen>(session.createQuery("from TblImagen where tblSolicitud=" + sol.getIdSolicitud() + " and not ( nombreImagen like '%crop%')").list());
                sol.setTblImagens(tblImagens);
                sol.setTblSolicitante((TblSolicitante) (session.createSQLQuery("select * from tbl_solicitante  solicitante left join tbl_solicitud solicitud on solicitud.id_solicitante=solicitante.id_solicitante\n"
                        + "where solicitud.id_solicitud=" + sol.getIdSolicitud() + "").addEntity("solicitante", TblSolicitante.class).uniqueResult()));
                sol.setTblEstado((TblEstado)(session.createSQLQuery("select * from tbl_estado estado where id_estado=" + sol.getTblEstado().getIdEstado() + "").addEntity("estado", TblEstado.class).uniqueResult()));
                }
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public TblSolicitud findSolicitudByIdSolicitante(TblSolicitante solicitante) {
        TblSolicitud solicitudModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblSolicitud WHERE id_Solicitante='" + solicitante.getIdSolicitante() + "'";
        System.out.println(sql);
        try {
            session.beginTransaction();
            solicitudModel = (TblSolicitud) session.createQuery(sql).uniqueResult();
            solicitudModel.setTblEstado((TblEstado)(session.createSQLQuery("select * from tbl_estado estado where id_estado=" + solicitudModel.getTblEstado().getIdEstado() + "").addEntity("estado", TblEstado.class).uniqueResult()));
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();            
        }
        return solicitudModel;
    }

    @Override
    public TblSolicitud findSolicitudBySerial(TblSolicitud solicitud) {
        TblSolicitud solicitudModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblSolicitud WHERE serial='" + solicitud.getSerial() + "'";
        System.out.println(sql);
        try {
            session.beginTransaction();
            solicitudModel = (TblSolicitud) session.createQuery(sql).uniqueResult();
            solicitudModel.setTblEstado((TblEstado)(session.createSQLQuery("select * from tbl_estado estado where id_estado=" + solicitudModel.getTblEstado().getIdEstado() + "").addEntity("estado", TblEstado.class).uniqueResult()));
            solicitudModel.setTblSolicitante((TblSolicitante)(session.createSQLQuery("select * from tbl_solicitante solicitante where id_solicitante=" + solicitudModel.getTblSolicitante().getIdSolicitante() + "").addEntity("soliciante", TblSolicitante.class).uniqueResult()));
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();           
        }
        return solicitudModel;

    }
}
