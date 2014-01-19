/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.jobs;

import com.siscacao.dao.SolicitudDao;
import com.siscacao.dao.SolicitudDaoImpl;
import com.siscacao.dao.UsuarioDao;
import com.siscacao.dao.UsuarioDaoImpl;
import com.siscacao.model.TblSolicitud;
import com.siscacao.model.TblUsuario;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Hanzo
 */
public class AsignacionJob implements Job {

    private SolicitudDao solicitudDao;
    private UsuarioDao usuarioDao;
    private TblUsuario tblUsuario;
    private List<TblSolicitud> listSolicitudes;

    public AsignacionJob() {
        solicitudDao = new SolicitudDaoImpl();
        usuarioDao = new UsuarioDaoImpl();
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        //Asignar solicitud a cosultor        
        tblUsuario = usuarioDao.retrieveLastUserToSolicitud();
        listSolicitudes = solicitudDao.retrieveListSolicitudPending();
        for (int i = 0; (i <= listSolicitudes.size()) && i<=5; i++) {
            solicitudDao.signedSolicitud(listSolicitudes.get(i), tblUsuario);           
        }
    }
}
