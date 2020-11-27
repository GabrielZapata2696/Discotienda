/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.discotienda.pojo.*;
import com.mycompany.discotienda.repo.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author SpacemanFromHell
 */
public class CompraService {

    public void RealizarCompra(Compra compra) {

        ObjectMapper mapper = new ObjectMapper();

        String jsonArticulos;
        try {
            jsonArticulos = mapper.writeValueAsString(compra.getListArticulos());
            compra.setArticulosSerializados(jsonArticulos);
            if (new DAOCompra().RealizarCompra(compra)) {
                this.Alerta("Compra realizada satisfactoriamente!", " EXITO!", FacesMessage.SEVERITY_INFO);
                return;
            } else {
                this.Alerta("Compra no realizada!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);
                return;
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CompraService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Alerta(String mensaje, String tipo, FacesMessage.Severity clase) {
        FacesMessage msg = new FacesMessage(clase, tipo, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
