/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblDepartamento;
import com.siscacao.model.TblVariedad;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class VariedadDaoImpl implements VariedadDao{

    @Override
    public List<TblVariedad> findAllVariedad() {
       List<TblVariedad> listVariedades = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            listVariedades = (List<TblVariedad>) session.createCriteria(TblVariedad.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listVariedades;
    }
     
}
