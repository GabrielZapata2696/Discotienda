/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.pojo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author SpacemanFromHell
 */
public class Compra {
    private int id;
    private int idUser;
    private Date fecha;
    private double total;
    private List<Factura> listArticulos;
    private String articulosSerializados;
   

    public Compra() {
    }       
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Factura> getListArticulos() {
        return listArticulos;
    }

    public void setListArticulos(List<Factura> listArticulos) {
        this.listArticulos = listArticulos;
    }

    public String getArticulosSerializados() {
        return articulosSerializados;
    }

    public void setArticulosSerializados(String articulosSerializados) {
        this.articulosSerializados = articulosSerializados;
    }

    
            
    
    
}
