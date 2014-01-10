/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblDepartamento;
import com.siscacao.model.TblSolicitante;
import com.siscacao.model.TblTipoDocumento;
import com.siscacao.util.HibernateConnectUtil;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class TipoDocumentoDaoImpl implements TipoDocumentoDao{

    @Override
    public List<TblTipoDocumento> findAllTypeDocument() {
      List<TblTipoDocumento> listTypeDocuments = null;
      Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblTipoDocumento";
        try {
            session.beginTransaction();
           // listUsersModel = (List<TblUsuario>)session.createQuery(sql).list();
            listTypeDocuments = (List<TblTipoDocumento>)session.createCriteria(TblTipoDocumento.class).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
      return listTypeDocuments;
    }
    
}
