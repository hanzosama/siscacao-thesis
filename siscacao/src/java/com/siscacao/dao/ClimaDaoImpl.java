/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblClima;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class ClimaDaoImpl implements ClimaDao {

    @Override
    public List<TblClima> findAllClimas() {
        List<TblClima> listClimas = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            // listUsersModel = (List<TblUsuario>)session.createQuery(sql).list();
            listClimas = (List<TblClima>) session.createCriteria(TblClima.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listClimas;
    }
}
