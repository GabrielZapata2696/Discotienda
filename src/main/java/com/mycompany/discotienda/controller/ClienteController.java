/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "clienteController")
@ViewScoped
public class ClienteController implements Serializable {

    private String nombre;

    @Inject
    private SessionKey sessionKey;
    
    @Inject
    private Carrito carrito;

    /**
     * Creates a new instance of ClienteController
     */
    public ClienteController() {
     
    }
    
    @PostConstruct
    public void init() {        
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(this.sessionKey.getLlave());
        this.mensage(user);     
        this.nombre = "Cliente";
        this.carrito.setIdCliente(user.getId());
       
    }

    public void mensage(Usuario user) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exito", "Bienvenido " + user.getNombre() + " " + user.getApellido()));
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

    /**
     * Getters y Setters
     *  
    */
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
    
}
