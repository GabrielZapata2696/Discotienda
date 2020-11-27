/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.*;
import com.mycompany.discotienda.service.CompraService;
import com.mycompany.discotienda.service.*;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "carritoController")
@ViewScoped
public class CarritoController implements Serializable {

    @Inject
    private SessionKey sessionKey;
    @Inject
    private Carrito carrito;

    private Factura factura;
    List<Factura> facturaFin;

    private List<Album> initAlbumes;
    private List<Cancion> initCanciones;
    private boolean validacion = false;

    ;
        

    /**
     * Creates a new instance of CarritoController
     */
    public CarritoController() {

    }

    @PostConstruct
    public void init() {
        Random rand = new Random();
        facturaFin = new ArrayList<Factura>();
        this.setInitAlbumes(this.carrito.getListaAlbumes());
        for (Album initAlbum : this.getInitAlbumes()) {
            this.factura = new Factura();
            this.factura.setId(rand.nextInt(300000));
            this.factura.setIdItem(initAlbum.getId_album());
            this.factura.setUrlImg(initAlbum.getUrl_img());
            this.factura.setNombre(initAlbum.getNombre());
            this.factura.setArtista(this.BuscarArtista(initAlbum.getId_artista()));
            this.factura.setPrecio(initAlbum.getPrecio());
            this.factura.setTipo("Album");
            facturaFin.add(this.factura);
        }
        this.setInitCanciones(this.carrito.getListaCanciones());
        for (Cancion initCancion : this.getInitCanciones()) {
            this.factura = new Factura();
            this.factura.setId(rand.nextInt(300000));
            this.factura.setIdItem(initCancion.getId());
            this.factura.setUrlImg("../../resources/images/cancion_img.jpg");
            this.factura.setNombre(initCancion.getNombre());
            this.factura.setArtista(this.BuscarArtista(initCancion.getId_artista()));
            this.factura.setPrecio(initCancion.getPrecio());
            this.factura.setTipo("Cancion");
            facturaFin.add(this.factura);
        }
        this.setFacturaFin(this.facturaFin);
        if (this.facturaFin != null) {
            this.validacion = true;
        }else if(this.facturaFin.isEmpty())
             this.validacion = false;

    }

    public String BuscarArtista(int idArtista) {
        return new ArtistaService().ObtenerNombreArtista(idArtista);
    }

    public void Retirar(int idPedido) {
        for (Factura factura1 : this.facturaFin) {
            if (factura1.getId() == idPedido) {

                this.facturaFin.remove(factura1);
                this.setFacturaFin(facturaFin);
                this.Alerta("Cancion retirada del carrito!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);
                break;
            }
        }
    }

    public void Comprar() {

        Compra compra = new Compra();
        compra.setIdUser(((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(this.sessionKey.getLlave())).getId());
        double acum = 0;        
        List<Factura> articulos = new ArrayList<Factura>();                      
        
        for (Factura factura1 : this.facturaFin) {
            acum = acum + factura1.getPrecio();
            articulos.add(factura1);
        }
        compra.setTotal(acum);        
        compra.setListArticulos(articulos);
        
        new CompraService().RealizarCompra(compra);
        this.facturaFin = new ArrayList<Factura>();
    }

    public void Alerta(String mensaje, String tipo, FacesMessage.Severity clase) {
        FacesMessage msg = new FacesMessage(clase, tipo, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccion(String pagina) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(pagina + ".xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarSesion() {
        this.sessionKey.setLlave(-1);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.redireccion("../../login");
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Factura> getFacturaFin() {
        return facturaFin;
    }

    public void setFacturaFin(List<Factura> facturaFin) {
        this.facturaFin = facturaFin;
    }

    public boolean isValidacion() {
        return validacion;
    }

    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }

    public List<Album> getInitAlbumes() {
        return initAlbumes;
    }

    public void setInitAlbumes(List<Album> initAlbumes) {
        this.initAlbumes = initAlbumes;
    }

    public List<Cancion> getInitCanciones() {
        return initCanciones;
    }

    public void setInitCanciones(List<Cancion> initCanciones) {
        this.initCanciones = initCanciones;
    }

}
