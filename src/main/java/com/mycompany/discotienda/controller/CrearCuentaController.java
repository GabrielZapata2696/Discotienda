/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Usuario;
import com.mycompany.discotienda.repo.DAOLogin;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.Pattern;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "crearController")
@ViewScoped
public class CrearCuentaController implements Serializable {

    private String correo;
    private String clave;
    private String nombre;
    private String apellido;
    private long documento;

    /**
     * Creates a new instance of CrearCuentaController
     */
    public CrearCuentaController() {
    }

    public void RegistrarNuevo() {
        Usuario user = new Usuario();
        user.setNombre(this.getNombre());
        user.setApellido(this.getApellido());
        user.setEmail(this.getCorreo());
        user.setPassword(this.getClave());
        user.setDocumento(this.getDocumento()+"");
        if(new DAOLogin().CrearUsuario(user)){
            this.Alerta("Cuenta creada Satisfactoriamente", " EXITO!!", FacesMessage.SEVERITY_INFO);
            this.redireccion("login");
        }else{
             this.Alerta("No se pudo crear la cuenta", " ERROR!!", FacesMessage.SEVERITY_ERROR);
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

    public String login() {
        return "login";
    }

    public String crear() {
        return "crearcuenta";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

}
