/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.dao;

import com.siscacao.util.HibernateConnectUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 *
 * @author Hanzo
 */
public class DashboardDaoImpl {

    public Map<String, Double> getGeneralReportNeuroph() {
        Map<String, Double> report = new HashMap<String, Double>();

        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sqlTotal = "select count(*) as total from tbl_respuesta_solicitud";
        String sqlTotalImage = "select count(*) as totalImage from tbl_respuesta_solicitud respuesta \n"
                + "left join tbl_diagnostico diagnostico on diagnostico.id_diagnostico = respuesta.id_diagnostico\n"
                + "left join tbl_diagnostico_imagen imagen on imagen.id_diagnostico = diagnostico.id_diagnostico\n"
                + "where imagen.id_patologia=diagnostico.id_patologia";
        String sqlTotalCaracteristica = "select count(*) as totalCaracteristica from tbl_respuesta_solicitud respuesta \n"
                + "left join tbl_diagnostico diagnostico on diagnostico.id_diagnostico = respuesta.id_diagnostico\n"
                + "left join tbl_diagnostico_caracteristica caracteristica on caracteristica.id_diagnostico = diagnostico.id_diagnostico\n"
                + "where caracteristica.id_patologia=diagnostico.id_patologia";
        try {
            session.beginTransaction();
            // get total assertions
            Double total = (Double) session.createSQLQuery(sqlTotal).addScalar("total", Hibernate.DOUBLE).uniqueResult();
            Double totalImage = (Double) session.createSQLQuery(sqlTotalImage).addScalar("totalImage", Hibernate.DOUBLE).uniqueResult();
            Double totalCaracteristica = (Double) session.createSQLQuery(sqlTotalCaracteristica).addScalar("totalCaracteristica", Hibernate.DOUBLE).uniqueResult();
            Double totalNone = (total - (totalImage + totalCaracteristica)) / total;

            Double image = (totalImage / total);
            Double caracteristicas = (totalCaracteristica / total);

            report.put("Imagen", image);
            report.put("Sintomas", caracteristicas);
            report.put("Experto", totalNone);

            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return report;

    }

    public Map<String, Double> getGeneralReportNeurophByDepartament(Long idDepartamento) {
        Map<String, Double> report = new HashMap<String, Double>();

        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sqlTotal = "select count(*) as total from tbl_respuesta_solicitud respuesta\n"
                + "left join tbl_solicitud solicitud on solicitud.id_solicitud = respuesta.id_solicitud\n"
                + "left join tbl_solicitante solicitante on solicitante.id_solicitante = solicitud.id_solicitante\n"
                + "where solicitante.id_departamento=" + idDepartamento + "";

        String sqlTotalMadness = "select count(*) as total, patologia.descripcion_patologia as name from tbl_respuesta_solicitud respuesta\n"
                + "left join tbl_diagnostico diagnostico on diagnostico.id_diagnostico = respuesta.id_diagnostico\n"
                + "left join tbl_solicitud solicitud on solicitud.id_solicitud = respuesta.id_solicitud\n"
                + "left join tbl_solicitante solicitante on solicitante.id_solicitante = solicitud.id_solicitante\n"
                + "left join tbl_patologia patologia on patologia.id_patologia=diagnostico.id_patologia WHERE solicitante.id_departamento=" + idDepartamento + " GROUP BY patologia.descripcion_patologia";
        try {
            session.beginTransaction();
            // get total assertions
            Double total = (Double) session.createSQLQuery(sqlTotal).addScalar("total", Hibernate.DOUBLE).uniqueResult();

            List<Object[]> result = session.createSQLQuery(sqlTotalMadness).addScalar("total", Hibernate.DOUBLE).addScalar("name", Hibernate.STRING).list();
            for (Object[] row : result) {
                report.put((String) row[1], ((Double) row[0] / total) * 100);
            }
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return report;

    }

    public Map<String, Double> getGeneralReportMadness() {
        Map<String, Double> report = new HashMap<String, Double>();

        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sqlTotal = "select count(*) as total from tbl_respuesta_solicitud";

        String sqlTotalMadness = "select count(*) as total, patologia.descripcion_patologia as name from tbl_respuesta_solicitud respuesta \n"
                + "left join tbl_diagnostico diagnostico on diagnostico.id_diagnostico = respuesta.id_diagnostico\n"
                + "left join tbl_patologia patologia on patologia.id_patologia=diagnostico.id_patologia GROUP BY patologia.descripcion_patologia";

        try {
            session.beginTransaction();
            // get total assertions
            Double total = (Double) session.createSQLQuery(sqlTotal).addScalar("total", Hibernate.DOUBLE).uniqueResult();

            List<Object[]> result = session.createSQLQuery(sqlTotalMadness).addScalar("total", Hibernate.DOUBLE).addScalar("name", Hibernate.STRING).list();
            for (Object[] row : result) {
                report.put((String) row[1], ((Double) row[0] / total) * 100);
            }
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return report;
    }

    public Double getTotalRequestOnSystem() {
        Double total = 0.0;
        Session session = HibernateConnectUtil.getSessionFactory().getCurrentSession();
        String sqlTotal = "select count(*) as total from tbl_respuesta_solicitud";
        try {
            session.beginTransaction();
            // get total assertions
            total = (Double) session.createSQLQuery(sqlTotal).addScalar("total", Hibernate.DOUBLE).uniqueResult();

            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        return total;
    }
}
