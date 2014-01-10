/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.TipoContactoDao;
import com.siscacao.model.TblTipoContacto;
import com.siscacao.util.HibernateConnectUtil;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class TipoContactoDaoImpl implements TipoContactoDao{

    @Override
    public TblTipoContacto findTipoContactoId(String nombreTipo) {
        TblTipoContacto tipoContacto = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblTipoContacto WHERE nombreTipo='" + nombreTipo + "'";
        try {
            session.beginTransaction();
            tipoContacto = (TblTipoContacto) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return tipoContacto;
    }
    
}
