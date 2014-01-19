/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblEstado;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class EstadoDaoImpl implements EstadoDao{

    @Override
    public List<TblEstado> findAllEstados() {
        List<TblEstado> listEstadoModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblEstado";
        try {
            session.beginTransaction();
            listEstadoModel = (List<TblEstado>) session.createQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listEstadoModel;
    }

    @Override
    public TblEstado findEstadoById(Long id) {
        
        TblEstado estadoModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblEstado WHERE idEstado=" + id + "";
        try {
            session.beginTransaction();
            estadoModel = (TblEstado) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return estadoModel;
    }
    
    
    
}
