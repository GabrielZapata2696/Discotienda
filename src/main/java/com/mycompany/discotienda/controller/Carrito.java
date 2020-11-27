/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "carrito")
@SessionScoped
public class Carrito implements Serializable {

    @Inject
    private SessionKey sessionKey;

    private int idCliente;
    private Date fecha;
    private List<Cancion> listaCanciones;
    private List<Album> listaAlbumes;
    private double total;

    /**
     * Creates a new instance of Carrito
     */
    public Carrito() {
        System.out.println("Se crea Carrito Session");
        this.fecha = Date.from(Instant.now());
        this.listaAlbumes = new ArrayList<Album>();
        this.listaCanciones = new ArrayList<Cancion>();
        this.total = 0;
        this.idCliente = -1;
    }

    @PostConstruct
    public void init() {
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(this.sessionKey.getLlave());
        this.idCliente = user.getId();

    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Cancion> getListaCanciones() {
        return listaCanciones;
    }

    public void setListaCanciones(List<Cancion> listaCanciones) {
        this.listaCanciones = listaCanciones;
    }

    public List<Album> getListaAlbumes() {
        return listaAlbumes;
    }

    public void setListaAlbumes(List<Album> listaAlbumes) {
        this.listaAlbumes = listaAlbumes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}
