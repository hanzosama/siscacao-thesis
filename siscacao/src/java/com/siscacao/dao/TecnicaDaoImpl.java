/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblDepartamento;
import com.siscacao.model.TblTecnicaCultivo;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class TecnicaDaoImpl implements TecnicaDao{

    @Override
    public List<TblTecnicaCultivo> findAllTecnicaCultivo() {
        
          List<TblTecnicaCultivo> listTecnicasCultivo = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            listTecnicasCultivo = (List<TblTecnicaCultivo>) session.createCriteria(TblTecnicaCultivo.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listTecnicasCultivo;
    }
    
}
