/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Usuario;
import com.mycompany.discotienda.service.LoginService;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Zapata
 * @author Mary Zapata
 * @since 19/11/2020
 * @version 1.0.0
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private String email;
    private String password;

    @Inject
    private SessionKey sessionKey;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    public String Login() {
        String redirect = "";
        FacesContext context = FacesContext.getCurrentInstance();
        LoginService loginService = new LoginService();
        Usuario user = loginService.login(this.email, this.password);
        if (user != null) {
            try {
                context.getExternalContext().getSessionMap().put(user.getDocumento(), user);

                Map<String, Object> mapSession = context.getExternalContext().getSessionMap();

                int key = Integer.parseInt(user.getDocumento());
                this.sessionKey.setLlave((Integer) key);
                this.sessionKey.setUser(user);
                //this.sessionKey.setSessionMap(mapSession);
                //session.setInstance(context);

                if (user.getRol() == 1) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("secure/admin/administrador.xhtml");
                } else if (user.getRol() == 2) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("secure/client/cliente.xhtml");
                }

            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!! ", "Credenciales incorrectas"));
            redirect = "login";
        }

        return redirect;
    }

    public String index() {
        return "index";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
