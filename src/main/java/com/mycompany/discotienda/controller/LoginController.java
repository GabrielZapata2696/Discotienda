/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }
    
}
