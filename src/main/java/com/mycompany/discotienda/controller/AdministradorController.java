/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Usuario;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "administradorController")
@ViewScoped
public class AdministradorController implements Serializable {

    private String nombre;

    @Inject
    private SessionKey sessionKey;

    /**
     * Creates a new instance of AdmiistradorController
     */
    public AdministradorController() {

    }

    @PostConstruct
    public void init() {
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(this.sessionKey.getLlave());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exito", "Bienvenido " + user.getNombre() + " " + user.getApellido()));
        this.nombre = "Pagina administrador";
    }

    
    
    
    public String inicioAdmin(){
        return "administrador";
    }
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
