/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.*;
import com.mycompany.discotienda.repo.DAOAdmin;
import com.mycompany.discotienda.service.AdminService;
import java.awt.event.ActionEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "comprarController")
@ViewScoped
public class ComprarController implements Serializable {

    @Inject
    private SessionKey sessionKey;
    @Inject
    private Carrito carrito;

    private String nombreArtista;
    private List<Artista> listadoArtistas;
    private List<String> listadoNombresArt;

    private List<Album> listadoAlbumes;
    private int id_Artista;
    private boolean visible = false;

    private List<Cancion> listadoCanciones;
    private int idAlbum;

    /**
     * Creates a new instance of ComprarController
     */
    public ComprarController() {
        this.ConsultarArtistas();
    }

    public void ConsultarArtistas() {
        this.listadoNombresArt = new ArrayList<String>();
        this.listadoArtistas = new AdminService().BuscarArtistas();
        for (Artista listadoArtista : listadoArtistas) {
            this.listadoNombresArt.add(listadoArtista.getNombre());
        }

    }

    public void onClickSelector(ActionEvent event) {
        for (Artista listadoArt : listadoArtistas) {
            if (listadoArt.getNombre().equals(this.nombreArtista)) {
                this.listadoAlbumes = new AdminService().BuscarAlbumes_XArtista(listadoArt.getId());
                this.setId_Artista(listadoArt.getId());
                this.setVisible(true);
            }
        }
    }

    public Album retornarAlbum(int idAlbum) {
        return new AdminService().BuscarAlbumXId(idAlbum);
    }

    public void onClickCanciones(int id_album) {
        System.out.println("id album: " + id_album);
        this.setListadoCanciones(new AdminService().BuscarCanciones_XAlbum(id_album));
        this.listadoCanciones = this.getListadoCanciones();
        this.idAlbum = id_album;
        this.setIdAlbum(this.idAlbum);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlg').show();");

    }

    public void onClickAddAlbum(String rowKey) {
        System.out.println("Agregar Album");
        this.Alerta("Album Agregado al carrito!", " ATENCIÓN", FacesMessage.SEVERITY_INFO);
        Album addAlbum = new DAOAdmin().Consultar_AlbumXId(Integer.parseInt(rowKey));
        System.out.println("album: "+addAlbum.getNombre() );
        this.carrito.getListaAlbumes().add(addAlbum);

    }

    public void agregarCancion(String rowKey) {
        System.out.println("Agregar Cancion");
        this.Alerta("Cancion Agregada al carrito!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);       
        Cancion addCancion = new DAOAdmin().Consultar_CancionXId(Integer.parseInt(rowKey));
        System.out.println("cancion: " + addCancion.getNombre());
        this.carrito.getListaCanciones().add(addCancion);
    }

    public void onCellEditCancion(CellEditEvent event) {
        System.out.println("Edit Cancion");
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

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public List<Artista> getListadoArtistas() {
        return listadoArtistas;
    }

    public void setListadoArtistas(List<Artista> listadoArtistas) {
        this.listadoArtistas = listadoArtistas;
    }

    public List<String> getListadoNombresArt() {
        return listadoNombresArt;
    }

    public void setListadoNombresArt(List<String> listadoNombresArt) {
        this.listadoNombresArt = listadoNombresArt;
    }

    public List<Album> getListadoAlbumes() {
        return listadoAlbumes;
    }

    public void setListadoAlbumes(List<Album> listadoAlbumes) {
        this.listadoAlbumes = listadoAlbumes;
    }

    public int getId_Artista() {
        return id_Artista;
    }

    public void setId_Artista(int id_Artista) {
        this.id_Artista = id_Artista;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<Cancion> getListadoCanciones() {
        return listadoCanciones;
    }

    public void setListadoCanciones(List<Cancion> listadoCanciones) {
        this.listadoCanciones = listadoCanciones;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

}
