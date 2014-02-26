package com.siscacao.services;
import com.siscacao.dao.DepartamentoDao;
import com.siscacao.dao.DepartamentoDaoImpl;
import com.siscacao.model.TblDepartamento;
import com.siscacao.objects.json.DepartamentoJson;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;

@Path("departamento")
@RequestScoped
public class DepartamentoResource {
    
    private List<TblDepartamento> departamentos;
    private DepartamentoDao departamentoDAO;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioResource
     */
    public DepartamentoResource() {
        this.departamentoDAO = new DepartamentoDaoImpl();
    }

    @GET
    @Path("registro")    
    @Produces("application/json")
    public List<DepartamentoJson>   buscarDepartamentos() {
        List<DepartamentoJson> depList = new ArrayList<DepartamentoJson>();
        this.departamentos= departamentoDAO.findAllDepartamento();
        for(TblDepartamento dep:this.departamentos){
            DepartamentoJson depto = new DepartamentoJson();
            depto.setId_departamento(String.valueOf(dep.getIdDepartamento()));
            depto.setNombre_departamento(dep.getNombreDepartamento());
        depList.add(depto);
        }
        return depList;
    }
}
