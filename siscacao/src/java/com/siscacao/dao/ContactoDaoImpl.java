/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblContacto;
import com.siscacao.model.TblTipoContacto;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class ContactoDaoImpl implements ContactoDao {

    @Override
    public List<TblContacto> findAllContactosByClientId(Long id) {
        List<TblContacto> contactos = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
       // String sql = "FROM TblContacto WHERE tblSolicitante.idSolicitante="+id+"";
        String sql = "SELECT * FROM  tbl_contacto contacto LEFT JOIN  tbl_tipo_contacto  tipo_contacto  ON tipo_contacto.id_tipo_contacto=contacto.id_tipo_contacto WHERE contacto.id_solicitante="+id+"";
        try {
            session.beginTransaction();
            contactos = (List<TblContacto>) session.createSQLQuery(sql).addEntity("contacto",TblContacto.class).list();
            for(TblContacto contacto: contactos){
             contacto.setTblTipoContacto((TblTipoContacto)session.createSQLQuery("select * from  tbl_tipo_contacto tipo_contacto where id_tipo_contacto=" + contacto.getTblTipoContacto().getIdTipoContacto()+  "").addEntity("tipo_contacto", TblTipoContacto.class).uniqueResult());
            
            }
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return contactos;
    }

    @Override
    public boolean createContacto(TblContacto contacto) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(contacto);
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
    public boolean updateContacto(TblContacto contacto) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(contacto);
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            session.beginTransaction().rollback();
        }
        return result;
    }
}
