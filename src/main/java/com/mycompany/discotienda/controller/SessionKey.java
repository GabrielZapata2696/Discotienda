/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author Gabriel Zapata
 * @author Mary Zapata
 * @since 20/11/2020
 * @version 1.0.0
 *
 */
@Named(value = "sessionKey")
@SessionScoped
public class SessionKey implements Serializable {

    private Integer llave;
    private FacesContext instance;
    private Map<String,Object> sessionMap;
    private Usuario user;

    /**
     * Creates a new instance of SessionKey
     */
    public SessionKey() {         
        this.llave = -1;                
    }
    
    

    public Integer getLlave() {
        return llave;
    }

    public void setLlave(Integer llave) {
        this.llave = llave;
    }

    public FacesContext getInstance() {
        return instance;
    }

    public void setInstance(FacesContext instance) {
        this.instance = instance;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    
    
}
