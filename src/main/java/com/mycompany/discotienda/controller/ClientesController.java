/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Usuario;
import com.mycompany.discotienda.service.AdminService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "clientesController")
@ViewScoped
public class ClientesController implements Serializable {

    private List<Usuario> listadoInicial;
    private AdminService adminService;
    private Usuario usuario;

    @Inject
    private SessionKey sessionKey;

    /**
     * Creates a new instance of ClientesController
     */
    public ClientesController() {
    }

    @PostConstruct
    public void init() {
        this.adminService = new AdminService();
        this.setListadoInicial(this.adminService.BuscarClientes());
        listadoInicial = this.getListadoInicial();
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        usuario = new Usuario();

        if (newValue != null && !newValue.equals(oldValue)) {
            if (newValue.toString().equals("") || newValue.toString().equals(" ")) {
                this.Alerta("No pueden haber campos vacios", " Error!!", FacesMessage.SEVERITY_FATAL);
            } else {
                if (new AdminService().EditarUsuario(Integer.parseInt(event.getRowKey()), event.getColumn().getHeaderText(), newValue)) {
                    this.Alerta("Cliente editado Satisfactoriamente!", " Exito", FacesMessage.SEVERITY_INFO);
                }
            }

        }
    }

    public void eliminar(String rowKey) {        
        if (new AdminService().EliminarCliente(Integer.parseInt(rowKey))) {
            this.Alerta("Cliente Eliminado!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);
            
        }
    }

    public void Alerta(String mensaje, String tipo, FacesMessage.Severity clase) {
        FacesMessage msg = new FacesMessage(clase, tipo, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccion(String pagina) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(pagina + ".xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarSesion() {
        this.sessionKey.setLlave(-1);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.redireccion("../../login");
    }

    public List<Usuario> getListadoInicial() {
        return listadoInicial;
    }

    public void setListadoInicial(List<Usuario> listadoInicial) {
        this.listadoInicial = listadoInicial;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
