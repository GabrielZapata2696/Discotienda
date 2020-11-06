/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Gabriel Zapata
 * @author Mary Zapata
 * @since 05/11/2020
 * @version 1.0.0
 */

@Named(value = "indexController")
@RequestScoped
public class IndexController {

    /**
     * Creates a new instance of IndexController
     */
    public IndexController() {
    }
    
    public String login(){
        return "login";    
    }
    
    
}
