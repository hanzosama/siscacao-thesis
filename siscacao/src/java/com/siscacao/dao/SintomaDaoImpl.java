/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblSintoma;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class SintomaDaoImpl implements SintomaDao{

    @Override
    public List<TblSintoma> getAllSintomas() {
        
        List<TblSintoma> listSintomas = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            // listUsersModel = (List<TblUsuario>)session.createQuery(sql).list();
            listSintomas = (List<TblSintoma>) session.createCriteria(TblSintoma.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listSintomas;
    }
    
}
