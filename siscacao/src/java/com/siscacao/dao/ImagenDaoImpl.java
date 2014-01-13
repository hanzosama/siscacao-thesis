/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblImagen;
import com.siscacao.util.HibernateConnectUtil;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class ImagenDaoImpl implements ImagenDao{

    @Override
    public boolean createImagen(TblImagen imagen) {
 boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(imagen);
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
    public boolean updateImagen(TblImagen imagen) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(imagen);
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
