/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblFuncionRol;
import com.siscacao.model.TblRol;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class FuncionRolDaoImpl implements FuncionesRolDao {

    @Override
    public boolean createFuncionRol(TblFuncionRol tblFuncionRol) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(tblFuncionRol);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public List<TblFuncionRol> findFuncionRolByRol(TblRol tblRol) {
        List<TblFuncionRol> tblFuncionRol = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblFuncionRol where tblRol = ?";
        System.out.println(sql);
        try {
            session.beginTransaction();
            tblFuncionRol = (List<TblFuncionRol>) session.createQuery(sql).setEntity(0, tblRol).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return tblFuncionRol;
    }

    @Override
    public boolean deleteFuncionRol(Long id) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            TblFuncionRol tblFuncionRol = (TblFuncionRol) session.load(TblFuncionRol.class, id);
            session.delete(tblFuncionRol);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }
}
