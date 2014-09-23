/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.FuncioesDaoImpl;
import com.siscacao.dao.FuncionRolDaoImpl;
import com.siscacao.dao.FuncionesDao;
import com.siscacao.dao.FuncionesRolDao;
import com.siscacao.dao.RolDao;
import com.siscacao.dao.RolDaoImpl;
import com.siscacao.i18n.diccionario;
import com.siscacao.model.TblFuncionRol;
import com.siscacao.model.TblFunciones;
import com.siscacao.model.TblRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@RequestScoped
public class RolBean implements Serializable {

    private List<TblRol> roles;
    private TblRol selectedRol;
    private List<String> funcionesSelected;
    private Map<String, Long> funciones;
    private FuncionesDao funcionDao;
    private FuncionesRolDao funcionesRolDao;
    private RolDao rolDao;
    private diccionario diccionario = new diccionario();

    public RolBean() {
        this.roles = new ArrayList<TblRol>();
        funcionDao = new FuncioesDaoImpl();
        funcionesRolDao = new FuncionRolDaoImpl();
        rolDao = new RolDaoImpl();
        List<TblFunciones> permisos = funcionDao.findAllFunciones();
        funciones = new HashMap<String, Long>();
        for (TblFunciones i : permisos) {
            funciones.put(i.getNombreFuncion(), i.getIdFuncion());
        }
        if (this.selectedRol == null) {
            selectedRol = new TblRol();
        }
    }

    public List<String> getFuncionesSelected() {
        if (funcionesSelected == null) {
            List<TblFuncionRol> tblFuncionRols = new ArrayList<TblFuncionRol>(selectedRol.getTblFuncionRols());
            List<String> funcionesSelected = new ArrayList<String>();
            for (TblFuncionRol i : tblFuncionRols) {
                for (Entry<String, Long> j : funciones.entrySet()) {
                    if (j.getValue().equals(i.getTblFunciones().getIdFuncion())) {
                        funcionesSelected.add(j.getValue().toString());
                    }
                }
            }
            this.funcionesSelected = funcionesSelected;
        }
        return this.funcionesSelected;
    }

    public void setFuncionesSelected(List<String> funcionesSelected) {
        this.funcionesSelected = funcionesSelected;
    }

    public Map<String, Long> getFunciones() {
        return funciones;
    }

    public TblRol getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(TblRol selectedRol) {
        this.selectedRol = selectedRol;

    }

    public List<TblRol> getRoles() {
        roles = rolDao.findAllRol();
        return roles;
    }

    public void createRol(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        RolDao rolDao = new RolDaoImpl();
        funcionesRolDao = new FuncionRolDaoImpl();
        List<TblRol> roles = rolDao.findAllRol();
        String msg;
        FacesMessage message;
        for (TblRol rol : roles) {
            if (rol.getNombreRol().toLowerCase().equals(this.selectedRol.getNombreRol().toLowerCase())) {
                msg = this.diccionario.getString("wrn_name_rol_exist");
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
                FacesContext.getCurrentInstance().addMessage(msg, message);
                return;
            }
        }
        Date currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        this.selectedRol.setFechaModificacion(currentDate);
        if (rolDao.createRol(this.selectedRol)) {
            setFuncionesRol();
            msg = this.diccionario.getString("created");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);

        } else {
            msg = this.diccionario.getString("error");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(msg, message);
    }

    public void updateSelectedRol(ActionEvent actionEvent) {
        RolDao rolDao = new RolDaoImpl();
        String msg;
        FacesMessage message;
        List<TblRol> roles = rolDao.findAllRol();
        for (TblRol rol : roles) {
            if (rol.getNombreRol().toLowerCase().equals(this.selectedRol.getNombreRol().toLowerCase()) && !rol.getIdRol().equals(this.selectedRol.getIdRol())) {
                msg = this.diccionario.getString("wrn_name_rol_exist");
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
                FacesContext.getCurrentInstance().addMessage(msg, message);
                return;
            }
        }
        Date currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        this.selectedRol.setFechaModificacion(currentDate);

        List<TblFuncionRol> tblFuncionRols2 = new ArrayList<TblFuncionRol>(funcionesRolDao.findFuncionRolByRol(this.selectedRol));
        List<TblFunciones> permisos = funcionDao.findAllFunciones();
        updateFunctionRolAdd(tblFuncionRols2, permisos);
        if (rolDao.updateRol(this.selectedRol)) {
            msg = this.diccionario.getString("updated");
            updateFuncionesRolDelete();
            setFuncionesSelected(funcionesSelected);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = this.diccionario.getString("error");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(msg, message);
    }

    public void deleteSelectedRol(ActionEvent actionEvent) {
        RolDao rolDao = new RolDaoImpl();
        String msg;
        FacesMessage message;
        if (rolDao.deleteRol(this.selectedRol.getIdRol())) {
            msg = this.diccionario.getString("deleted");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = this.diccionario.getString("error");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(msg, message);
    }

    private void setFuncionesRol() {
        List<TblFunciones> permisos = funcionDao.findAllFunciones();
        for (TblFunciones i : permisos) {
            for (String permiso : funcionesSelected) {
                if (permiso.equals(String.valueOf(i.getIdFuncion()))) {
                    TblFuncionRol tblFuncionRol = new TblFuncionRol();
                    tblFuncionRol.setTblRol(selectedRol);
                    tblFuncionRol.setTblFunciones(i);
                    selectedRol.getTblFuncionRols().add(tblFuncionRol);
                    funcionesRolDao.createFuncionRol(tblFuncionRol);
                }
            }

        }
    }

    public void updateFuncionesRolDelete() {
        selectedRol.getTblFuncionRols().addAll(funcionesRolDao.findFuncionRolByRol(selectedRol));
        for (Entry<String, Long> j : funciones.entrySet()) {
            if (!funcionesSelected.contains(j.getValue().toString())) {
                List<TblFuncionRol> tblFuncionRols = new ArrayList<TblFuncionRol>(selectedRol.getTblFuncionRols());
                for (TblFuncionRol i : tblFuncionRols) {
                    if (i.getTblFunciones().getIdFuncion().equals(j.getValue())) {
                        funcionesRolDao.deleteFuncionRol(i.getIdFuncionRol());
                    }
                }
            }
        }

    }

    private void updateFunctionRolAdd(List<TblFuncionRol> tblFuncionRols2, List<TblFunciones> permisos) throws NumberFormatException {
        for (String permiso : funcionesSelected) {
            boolean exist = false;
            for (TblFuncionRol i : tblFuncionRols2) {
                if (permiso.equals(String.valueOf(i.getTblFunciones().getIdFuncion()))) {
                    exist = true;
                }
            }

            if (!exist) {
                TblFuncionRol tblFuncionRol = new TblFuncionRol();
                tblFuncionRol.setTblRol(selectedRol);
                for (TblFunciones j : permisos) {
                    if (j.getIdFuncion().equals(Long.valueOf(permiso))) {
                        tblFuncionRol.setTblFunciones(j);
                    }
                }
                selectedRol.getTblFuncionRols().add(tblFuncionRol);
            }
        }
    }
}
