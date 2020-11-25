/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Cancion;
import com.mycompany.discotienda.repo.DAOAdmin;
import com.mycompany.discotienda.service.AdminService;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "dlgCancion")
@ViewScoped
public class DialogCancionesController implements Serializable {

    private List<Cancion> listadoCanciones;
    
    @Inject
    private SessionKey sessionKey;
    
    /**
     * Creates a new instance of DialogCancionesController
     */
    public DialogCancionesController(int idAlbum) {        
        System.out.println("entra a dialogo---id"+idAlbum);
        this.setListadoCanciones(new AdminService().BuscarCanciones_XAlbum(idAlbum));
        PrimeFaces.current().dialog().openDynamic("dialogCanciones.xhtml");
    }

    public void onCellEdit(){
        System.out.println("Entro a OnCellEdit dialogo cancion");
    }
    
    public void eliminar(int idCancion){
        System.out.println("Entro a eliminar cancion--id"+idCancion);
    }
    
    
    public List<Cancion> getListadoCanciones() {
        return listadoCanciones;
    }

    public void setListadoCanciones(List<Cancion> listadoCanciones) {
        this.listadoCanciones = listadoCanciones;
    }
    
    
    
}
