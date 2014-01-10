/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblUsuario;
import com.siscacao.util.CriptoUtils;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.FetchMode;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author Hanzo
 */
public class UsuarioDaoImpl implements UsuarioDao {

    @Override
    public TblUsuario findUserbyCuenta(TblUsuario Usuario) {
        TblUsuario userModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblUsuario WHERE cuenta='" + Usuario.getCuenta() + "'";
        System.out.println(sql);
        try {
            session.beginTransaction();
            userModel = (TblUsuario) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return userModel;
    }

    @Override
    public TblUsuario login(TblUsuario Usuario) {
        TblUsuario userModel = this.findUserbyCuenta(Usuario);
        if (userModel != null) {
            try{
            if (!userModel.getContrasena().equals(CriptoUtils.MD5Digest(Usuario.getContrasena()))) {
                userModel = null;
            }
            }catch(Exception e){
                System.err.println(e);
                userModel=null;
            }
        }
        return userModel;
    }

    @Override
    public List<TblUsuario> findAllUsers() {
        List<TblUsuario> listUsersModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblUsuario";
        try {
            session.beginTransaction();
           // listUsersModel = (List<TblUsuario>)session.createQuery(sql).list();
            listUsersModel = (List<TblUsuario>)session.createCriteria(TblUsuario.class).addOrder(Order.asc("cuenta")).setFetchMode("tblUsuarioRols", FetchMode.EAGER).setFetchMode("tblRol", FetchMode.EAGER).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return listUsersModel;
    }

    @Override
    public boolean createUser(TblUsuario usuario) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(usuario) ;
            session.beginTransaction().commit();
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
            result=false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public boolean updateUser(TblUsuario usuario) {
        
         boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(usuario) ;
            session.beginTransaction().commit();
            result=true;
        } catch (Exception e) {
            result=false;
            session.beginTransaction().rollback();
        }
        return result;
         }

    @Override
    public boolean deleteUser(Long id) {
         boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            TblUsuario usuario=(TblUsuario) session.load(TblUsuario.class, id);
            session.delete(usuario) ;
            session.beginTransaction().commit();
            result=true;
        } catch (Exception e) {
            result=false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public boolean resetPwdUser(Long id, String Pwd) {
       boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            TblUsuario usuario=(TblUsuario) session.load(TblUsuario.class, id);
            usuario.setContrasena(Pwd);
            session.update(usuario);
            session.beginTransaction().commit();
            result=true;
        } catch (Exception e) {
            result=false;
            session.beginTransaction().rollback();
        }
        return result;
    }
}
