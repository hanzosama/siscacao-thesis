/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblUsuario;
import com.siscacao.util.CriptoUtils;
import com.siscacao.util.HibernateConnectUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    public TblUsuario findUserbyId(Integer idUsuario) {
        TblUsuario userModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblUsuario WHERE idUsuario=" + idUsuario + "";
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
            try {
                if (!userModel.getContrasena().equals(CriptoUtils.MD5Digest(Usuario.getContrasena()))) {
                    userModel = null;
                }
            } catch (Exception e) {
                System.err.println(e);
                userModel = null;
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
            listUsersModel = (List<TblUsuario>) session.createCriteria(TblUsuario.class).addOrder(Order.asc("cuenta")).setFetchMode("tblUsuarioRols", FetchMode.EAGER).setFetchMode("tblRol", FetchMode.EAGER).list();
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
            session.save(usuario);
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
    public boolean updateUser(TblUsuario usuario) {

        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(usuario);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
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
            TblUsuario usuario = (TblUsuario) session.load(TblUsuario.class, id);
            session.delete(usuario);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
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
            TblUsuario usuario = (TblUsuario) session.load(TblUsuario.class, id);
            usuario.setContrasena(Pwd);
            session.update(usuario);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }

    @Override
    public TblUsuario retrieveLastUserToSolicitud() {
        TblUsuario userModel = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "select MIN(result.total)as numero , usuario.* from tbl_usuario as usuario left join (select COUNT(asignacion.id_asignacion_solicitud) as total, usuario.id_usuario from tbl_asignacion_solicitud as asignacion \n"
                + "	left join tbl_solicitud as solicitud on solicitud.id_solicitud=asignacion.id_solicitud\n"
                + "	right join tbl_usuario as usuario on usuario.id_usuario=asignacion.id_usuario \n"
                + "	left join tbl_estado as estado on estado.id_estado=solicitud.id_estado where solicitud.id_estado = 1 or asignacion.id_usuario is null group by usuario.id_usuario)result on result.id_usuario=usuario.id_usuario left join tbl_usuario_rol user_rol on user_rol.id_usuario=usuario.id_usuario where user_rol.id_rol=118  group by usuario.id_usuario order by numero asc";
        try {
            session.beginTransaction();
            userModel = (TblUsuario) session.createSQLQuery(sql).addEntity("usuario",TblUsuario.class).list().get(0);
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return userModel;
    }
}
