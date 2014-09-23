/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import com.siscacao.dao.RolDao;
import com.siscacao.dao.RolDaoImpl;
import com.siscacao.dao.UsuarioDao;
import com.siscacao.dao.UsuarioDaoImpl;
import com.siscacao.dao.UsuarioRolDao;
import com.siscacao.dao.UsuarioRolDaoImpl;
import com.siscacao.i18n.diccionario;
import com.siscacao.model.TblRol;
import com.siscacao.model.TblUsuario;
import com.siscacao.model.TblUsuarioRol;
import com.siscacao.util.CriptoUtils;
import java.io.Serializable;
import javax.faces.event.ActionEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Hanzo
 */
@ManagedBean
@RequestScoped
public class UsuarioBean implements Serializable {

    private List<TblUsuario> usuarios;
    private List<TblUsuario> filteredUsers;
    private TblUsuario selectedUser;
    private UsuarioDao usuarioDao;
    private RolDao rolDao;
    private UsuarioRolDao usuarioRolDao;
    private String rol;
    private Map<String, String> roles = new HashMap<String, String>();
    private List<TblRol> listRoles;
    private String oldPwd = "";
    private String newPwd = "";
    private diccionario diccionario = new diccionario();

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
        this.usuarios = new ArrayList<TblUsuario>();
        usuarioDao = new UsuarioDaoImpl();
        if (this.selectedUser == null) {
            selectedUser = new TblUsuario();
        }
        rolDao = new RolDaoImpl();
        listRoles = new ArrayList<TblRol>(rolDao.findAllRol());
        for (TblRol rol : listRoles) {
            this.roles.put(rol.getNombreRol(), rol.getNombreRol());
        }
        usuarioRolDao = new UsuarioRolDaoImpl();
    }

    public List<TblUsuario> getUsuarios() {
        usuarios = usuarioDao.findAllUsers();
        return usuarios;
    }

    public TblUsuario getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(TblUsuario selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = CriptoUtils.MD5Digest(oldPwd);
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = CriptoUtils.MD5Digest(newPwd);
    }

    public List<TblUsuario> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<TblUsuario> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public void createSelectedUser(ActionEvent actionEvent) {
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        String msg;
        FacesMessage message;
        Date currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        this.selectedUser.setFechaRegistro(currentDate);
        List<TblUsuario> tblUsuarios = usuarioDao.findAllUsers();
        setRolSelectedtUser(this.rol);
        for (TblUsuario tblUsuario : tblUsuarios) {
            if (tblUsuario.getCuenta().toLowerCase().equals(this.selectedUser.getCuenta().toLowerCase())) {
                msg = this.diccionario.getString("wrn_account_name_already_exist");
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, this.diccionario.getString("wrn_account_name_already_exist_detail"));
                FacesContext.getCurrentInstance().addMessage(msg, message);
                return;
            }
        }
        this.getSelectedUser().setContrasena(CriptoUtils.MD5Digest(this.selectedUser.getContrasena()));
        if (usuarioDao.createUser(this.selectedUser)) {
            msg = this.diccionario.getString("created");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);

        } else {
            msg = this.diccionario.getString("error");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(msg, message);
    }

    public void updateSelectedUser(ActionEvent actionEvent) {
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        String msg;
        FacesMessage message;
        Date currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        this.selectedUser.setFechaRegistro(currentDate);
        Set<TblUsuarioRol> tmpTblUsuarioRol = new HashSet<TblUsuarioRol>();
        tmpTblUsuarioRol.add(usuarioRolDao.retriveFuncionByuser(this.selectedUser));
        for (TblRol rol : listRoles) {
            if (rol.getNombreRol().equals(this.selectedUser.getRol())) {
                tmpTblUsuarioRol.iterator().next().setTblRol(rol);
                this.selectedUser.setTblUsuarioRols(tmpTblUsuarioRol);
            }
        }
        if (usuarioDao.updateUser(this.selectedUser)) {
            msg = this.diccionario.getString("updated");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = this.diccionario.getString("error");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(msg, message);
    }

    public void deleteSelectedUser(ActionEvent actionEvent) {
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        String msg;
        FacesMessage message;
        if (usuarioDao.deleteUser(this.selectedUser.getIdUsuario())) {
            msg = this.diccionario.getString("deleted");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = this.diccionario.getString("error");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(msg, message);
    }

    public void resetPwdSelectedUser(ActionEvent actionEvent) {
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        String msg;
        FacesMessage message;
        Set<TblUsuarioRol> tmpTblUsuarioRol = new HashSet<TblUsuarioRol>();
        System.out.println("old pass bd " + this.selectedUser.getContrasena());
        System.out.println("old pass act" + this.oldPwd);
        System.out.println("new pass" + this.newPwd);

        if (!this.selectedUser.getContrasena().toString().equals(this.oldPwd)) {
            msg = this.diccionario.getString("wrn_previous_pass_not_match");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        } else if (!this.newPwd.equals(this.oldPwd)) {
            if (usuarioDao.resetPwdUser(this.selectedUser.getIdUsuario(), newPwd)) {
                msg = this.diccionario.getString("lbl_pass_rest_success");
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            } else {
                msg = this.diccionario.getString("error");
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            }
        } else {
            msg = this.diccionario.getString("lbl_enter_other_pass_to_current");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(msg, message);
    }

    private void setRolSelectedtUser(String selectedRol) {
        for (TblRol rol : listRoles) {
            if (rol.getNombreRol().equals(selectedRol)) {
                Set<TblUsuarioRol> a = new HashSet<TblUsuarioRol>();
                TblUsuarioRol usuarioRol = new TblUsuarioRol();
                usuarioRol.setTblRol(rol);
                usuarioRol.setTblUsuario(selectedUser);
                a.add(usuarioRol);
                this.selectedUser.setTblUsuarioRols(a);
            }
        }
    }
}
