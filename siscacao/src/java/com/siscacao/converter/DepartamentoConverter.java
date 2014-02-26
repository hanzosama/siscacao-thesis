/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.converter;

import com.siscacao.dao.DepartamentoDao;
import com.siscacao.dao.DepartamentoDaoImpl;
import com.siscacao.model.TblDepartamento;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Hanzo
 */
@FacesConverter("DepartamentoConvert")
public class DepartamentoConverter implements Converter {

    private String selectedDepartamento;
    private List<TblDepartamento> listDepartamentos;
    private DepartamentoDao departamentoDao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return string;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
         departamentoDao = new DepartamentoDaoImpl();
        listDepartamentos = departamentoDao.findAllDepartamento();
        for (TblDepartamento dep : listDepartamentos) {
            if (dep.getIdDepartamento().toString().equals(o.toString())) {
                return dep.getNombreDepartamento();
            }
        }
        return "Hola";
    }
}
