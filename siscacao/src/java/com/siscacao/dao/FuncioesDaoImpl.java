/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblFunciones;
import com.siscacao.model.TblRol;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class FuncioesDaoImpl implements FuncionesDao{

    @Override
    public List<TblFunciones> findAllFunciones() {
        
        List<TblFunciones> listFuncionesModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblFunciones";
        try {
            session.beginTransaction();
            listFuncionesModel = (List<TblFunciones>) session.createQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listFuncionesModel;
    }
    
}
