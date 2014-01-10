/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblRol;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class RolDaoImpl implements RolDao {

    @Override
    public List<TblRol> findAllRol() {
        List<TblRol> listRolsModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        //String sql = "FROM TblRol";
        String sql = "SELECT distinct r FROM TblRol  as r left join fetch r.tblFuncionRols";
        try {
            session.beginTransaction();
            listRolsModel = (List<TblRol>) session.createQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listRolsModel;
    }

    @Override
    public boolean createRol(TblRol rol) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(rol);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public boolean updateRol(TblRol rol) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(rol);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public boolean deleteRol(Long id) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            TblRol rol = (TblRol) session.load(TblRol.class, id);
            session.delete(rol);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }
}
