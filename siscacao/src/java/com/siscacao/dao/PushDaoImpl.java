/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblPushDevice;
import com.siscacao.util.HibernateConnectUtil;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class PushDaoImpl implements PushDao {

    @Override
    public TblPushDevice findPushByIdentification(String identification) {

        TblPushDevice pushDevice = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        //String sql = "FROM TblRol";
        String sql = "FROM TblPushDevice  WHERE  numero_documento ='"+identification+"' ";
        try {
            session.beginTransaction();
            pushDevice = (TblPushDevice) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return pushDevice;
    }

    @Override
    public boolean createPushDevice(TblPushDevice pushDevice) {

        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(pushDevice);
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
    public boolean deletePushDevice(Long id) {

        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            TblPushDevice pushDevice = (TblPushDevice) session.load(TblPushDevice.class, id);
            session.delete(pushDevice);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public boolean updateRol(TblPushDevice pushDevice) {
        
         boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(pushDevice);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }
    
    
}
