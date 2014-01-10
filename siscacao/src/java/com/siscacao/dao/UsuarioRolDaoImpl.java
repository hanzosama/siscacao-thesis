/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblUsuario;
import com.siscacao.model.TblUsuarioRol;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hanzo
 */
public class UsuarioRolDaoImpl implements UsuarioRolDao{

    @Override
    public TblUsuarioRol retriveFuncionByuser(TblUsuario tblUsuario) {
         TblUsuarioRol tblUsuarioRol = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            tblUsuarioRol = (TblUsuarioRol)session.createCriteria(TblUsuarioRol.class).add(Restrictions.eq("tblUsuario",tblUsuario)).setFetchMode("tblRol", FetchMode.EAGER).list().get(0);
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return tblUsuarioRol;
    }
    
}
