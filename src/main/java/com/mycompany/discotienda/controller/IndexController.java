/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Zapata
 * @author Mary Zapata
 * @since 05/11/2020
 * @version 1.0.0
 */
@Named(value = "indexController")
@ViewScoped
public class IndexController implements Serializable {

    @Inject
    private SessionKey sessionKey ;
     
    /**
     * Creates a new instance of IndexController
     */
    public IndexController() {
    }

    @PostConstruct
    public void init() {        
       

    }

    public String login() {
        return "login";
    }
    
    public String crear() {
        return "crearcuenta";
    }

}
