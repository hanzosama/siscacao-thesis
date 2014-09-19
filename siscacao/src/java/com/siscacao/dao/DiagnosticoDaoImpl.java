/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.model.TblDiagnostico;
import com.siscacao.model.TblDiagnosticoCaracteristica;
import com.siscacao.model.TblDiagnosticoImagen;
import com.siscacao.util.HibernateConnectUtil;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class DiagnosticoDaoImpl implements DiagnosticoDao {

    @Override
    public boolean createDiagnosticoImagen(TblDiagnosticoImagen diagnosticoImagen) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(diagnosticoImagen);
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
    public boolean createDiagnosticoSintoma(TblDiagnosticoCaracteristica diagnosticoCaracteristica) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(diagnosticoCaracteristica);
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
    public boolean createDiagnosticoGeneral(TblDiagnostico diagnostico) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(diagnostico);
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
    public TblDiagnosticoImagen getTblDiagnosticoImagenByGeneralDiagnotico(TblDiagnostico diagnostico) {
        TblDiagnosticoImagen diagnosticoImagen = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblDiagnosticoImagen WHERE tblDiagnosticoByIdDiagnostico = ?";
        try {
            session.beginTransaction();
            diagnosticoImagen = (TblDiagnosticoImagen) session.createQuery(sql).setEntity(0, diagnostico).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return diagnosticoImagen;
    }

    @Override
    public TblDiagnosticoCaracteristica getTblDiagnosticoCaracteristicaByGeneralDiagnotico(TblDiagnostico diagnostico) {
        TblDiagnosticoCaracteristica caracteristica = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblDiagnosticoCaracteristica WHERE tblDiagnosticoByIdDiagnostico = ?";
        try {
            session.beginTransaction();
            caracteristica = (TblDiagnosticoCaracteristica) session.createQuery(sql).setEntity(0, diagnostico).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return caracteristica;
    }

    @Override
    public TblDiagnostico getTblDiagnosticoById(Long id) {

        TblDiagnostico diagnostico = null;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM TblDiagnostico WHERE idDiagnostico = " + id + "";
        try {
            session.beginTransaction();
            diagnostico = (TblDiagnostico) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return diagnostico;
    }

    @Override
    public boolean updateDiagnosticoImage(TblDiagnosticoImagen diagnosticoImagen) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(diagnosticoImagen);
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
    public boolean updateDiagnosticoImage(TblDiagnosticoCaracteristica caracteristica) {
        boolean result;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(caracteristica);
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
