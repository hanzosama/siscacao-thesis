/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblDepartamento;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class DepartamentoDaoImpl implements DepartamentoDao {


    @Override
    public List<TblDepartamento> findAllDepartamento() {
        List<TblDepartamento> listDerpartamentos = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblDepartamento";
        try {
            session.beginTransaction();
            // listUsersModel = (List<TblUsuario>)session.createQuery(sql).list();
            listDerpartamentos = (List<TblDepartamento>) session.createCriteria(TblDepartamento.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listDerpartamentos;
    }
}
