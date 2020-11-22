/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.help;

import com.mycompany.discotienda.controller.SessionKey;
import com.mycompany.discotienda.pojo.Usuario;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "securityFilter")
@SessionScoped
public class SecurityFilter implements Serializable, Filter {

    @Inject
    private SessionKey sessionKey;

    /**
     * Creates a new instance of SecurityFilter
     */
    public SecurityFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Entro al filtro");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURL().toString();
        // FacesContext context = this.sessionKey.getInstance();
        if (this.sessionKey.getLlave() < 0) {
            System.out.println("Entro a negativo");
            res.sendRedirect("../login.xhtml");

        } else {

            if (this.sessionKey.getSessionMap() == null) {
                //redireccionar
                System.out.println("Entro a instancia nula");
                res.sendRedirect("../login.xhtml");

            } else {
                //(Usuario) this.sessionKey.getSessionMap().get(this.sessionKey.getLlave() + "");
                Usuario user = this.sessionKey.getUser();

                if (user != null) {
                    System.out.println("Entro filtro admin");
                    if (url.contains("admin") && user.getRol() == 1) {
                        chain.doFilter(request, response);

                    } else if (url.contains("client") && user.getRol() == 2) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("../login.xhtml");
                    }

                } else {
                    System.out.println("Entro filtro user null");
                    res.sendRedirect("../login.xhtml");
                }
            }
        }

    }

    @Override
    public void destroy() {
    }

}
