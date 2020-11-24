/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.service;

import com.mycompany.discotienda.controller.SessionKey;
import com.mycompany.discotienda.pojo.Usuario;
import com.mycompany.discotienda.repo.DAOLogin;
import java.util.Map;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Zapata
 * @author Mary Zapata
 * @since 19/11/2020
 * @version 1.0.0
 *
 */

public class LoginService {

    @Inject
    private SessionKey sessionKey;

    public Usuario login(String email, String password) {

        Usuario user = new DAOLogin().AutenticarUsuario(email, password);
        FacesContext context = FacesContext.getCurrentInstance();
        if (user != null) {
            context.getExternalContext().getSessionMap().put(user.getDocumento() + "", user);
            Map<String,Object> mapSession = context.getExternalContext().getSessionMap();
            this.sessionKey = new SessionKey();            
            int key = Integer.parseInt(user.getDocumento());      
            this.sessionKey.setInstance(context);
            this.sessionKey.setLlave((Integer) key);
            this.sessionKey.setUser(user);
            this.sessionKey.setSessionMap(mapSession);
            System.out.println("Llave: "+ this.sessionKey.getLlave());
        } else {

            context.getExternalContext().getSessionMap().put(-1 + "", null);
        }
        return user;
    }
    
    
    

}
