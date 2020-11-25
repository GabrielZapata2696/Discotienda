/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Album;
import com.mycompany.discotienda.pojo.Artista;
import com.mycompany.discotienda.pojo.Cancion;
import com.mycompany.discotienda.repo.DAOAdmin;
import com.mycompany.discotienda.service.AdminService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Named(value = "albumesController")
@ViewScoped
public class AlbumesController implements Serializable {

    private List<Artista> listadoArtistas;
    private List<String> listadoNombresArt;
    private List<Album> listadoAlbumes;
    private List<Cancion> listadoCanciones;
    private String nombreArtista;
    private Artista artista;
    private int id_Artista;
    private Album album;
    private Cancion cancion;
    private boolean visible = false;

    @Inject
    private SessionKey sessionKey;

    /**
     * Creates a new instance of AlbumesController
     */
    public AlbumesController() {
        this.ConsultarArtistas();
    }

    public void ConsultarArtistas() {
        this.listadoNombresArt = new ArrayList<String>();
        this.listadoArtistas = new AdminService().BuscarArtistas();
        for (Artista listadoArtista : listadoArtistas) {
            this.listadoNombresArt.add(listadoArtista.getNombre());
        }

    }

    public void onClickselector(ActionEvent event) {
        for (Artista listadoArt : listadoArtistas) {
            if (listadoArt.getNombre().equals(this.nombreArtista)) {
                System.out.println("Artista: " + this.nombreArtista + " idArtista: " + listadoArt.getId());
                this.listadoAlbumes = new AdminService().BuscarAlbumes_XArtista(listadoArt.getId());
                this.setVisible(true);
            }
        }
    }

    public void onClickCanciones(int id_album) {
        this.setListadoCanciones(new AdminService().BuscarCanciones_XAlbum(id_album));
        this.listadoCanciones = this.getListadoCanciones();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlg').show();");

    }

    public void eliminarCancion(String rowKey) {         
        if (new AdminService().EliminarCancion(rowKey)) {
            this.Alerta("Cancion Eliminada!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);
            
        }else{
             System.out.println("Retorna Falso");
        }
    }

    public void onCellEditAlbumes(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();        
        this.album = new Album();        
        if (newValue != null && !newValue.equals(oldValue)) {
            if (newValue.toString().equals("") || newValue.toString().equals(" ")) {
                this.Alerta("No pueden haber campos vacios", " Error!!", FacesMessage.SEVERITY_FATAL);
            } else {
                if (new AdminService().EditarAlbum(Integer.parseInt(event.getRowKey()), event.getColumn().getHeaderText(), newValue)) {
                    this.Alerta("Album editado Satisfactoriamente!", " Exito", FacesMessage.SEVERITY_INFO);
                }
            }
        }
    }
    
    public void onCellEditCancion(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();        
        this.cancion = new Cancion();        
        if (newValue != null && !newValue.equals(oldValue)) {
            if (newValue.toString().equals("") || newValue.toString().equals(" ")) {
                this.Alerta("No pueden haber campos vacios", " Error!!", FacesMessage.SEVERITY_FATAL);
            } else {
                if (new AdminService().EditarCancion(Integer.parseInt(event.getRowKey()), event.getColumn().getHeaderText(), newValue)) {
                    this.Alerta("Cancion editado Satisfactoriamente!", " Exito", FacesMessage.SEVERITY_INFO);
                }
            }
        }
    }
    
    public void onClickElimAlbum(String rowKey){     
        if (new AdminService().EliminarAlbum(rowKey)) {
            this.Alerta("Album Eliminado!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);            
        }else{
            this.Alerta("El album contiene canciones activas!", " Error!!", FacesMessage.SEVERITY_ERROR);            
            System.out.println("Retorna Falso");
        }
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

    public List<Artista> getListadoArtistas() {
        return listadoArtistas;
    }

    public void setListadoArtistas(List<Artista> listadoArtistas) {
        this.listadoArtistas = listadoArtistas;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public int getId_Artista() {
        return id_Artista;
    }

    public void setId_Artista(int id_Artista) {
        this.id_Artista = id_Artista;
    }

    public List<String> getListadoNombresArt() {
        return listadoNombresArt;
    }

    public void setListadoNombresArt(List<String> listadoNombresArt) {
        this.listadoNombresArt = listadoNombresArt;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public List<Album> getListadoAlbumes() {
        return listadoAlbumes;
    }

    public void setListadoAlbumes(List<Album> listadoAlbumes) {
        this.listadoAlbumes = listadoAlbumes;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

}
