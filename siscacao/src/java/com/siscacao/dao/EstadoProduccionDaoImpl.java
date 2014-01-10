/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblEstadoProduccion;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class EstadoProduccionDaoImpl implements EstadoProduccionDao {

    @Override
    public List<TblEstadoProduccion> findAllEstadoProduccionDao() {
        List<TblEstadoProduccion> listEstadosProduccion = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            listEstadosProduccion = (List<TblEstadoProduccion>) session.createCriteria(TblEstadoProduccion.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listEstadosProduccion;
    }
}
