/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.controller;

import com.mycompany.discotienda.pojo.Album;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import com.mycompany.discotienda.pojo.Artista;
import com.mycompany.discotienda.service.*;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author SpacemanFromHell
 */
@Named(value = "artistaController")
@ViewScoped
public class ArtistaController implements Serializable {

    List<Artista> listadoArtistas;
    Artista artista;
    @Inject
    private SessionKey sessionKey;

    /*
    *   Variables para crear un artista
     */
    private String nombre;
    private String descripcion;
    private UploadedFile urlImg;
    private String fileName;

    /**
     * Creates a new instance of ArtistaController
     */
    public ArtistaController() {

    }

    @PostConstruct
    public void init() {
        this.listadoArtistas = new ArtistaService().BuscarArtistas();
    }

    public void onCellEditArtista(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        this.artista = new Artista();
        if (newValue != null && !newValue.equals(oldValue)) {
            if (newValue.toString().equals("") || newValue.toString().equals(" ")) {
                this.Alerta("No pueden haber campos vacios", " Error!!", FacesMessage.SEVERITY_FATAL);
            } else {
                if (new ArtistaService().EditarArtista(Integer.parseInt(event.getRowKey()), event.getColumn().getHeaderText(), newValue)) {
                    this.Alerta("Artista editado Satisfactoriamente!", " Exito", FacesMessage.SEVERITY_INFO);
                }
            }
        }
    }

    public void onClickElimArt(String rowKey) {
        if (new ArtistaService().EliminarArtista(rowKey)) {
            this.Alerta("Artista Eliminado!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);
        } else {
            this.Alerta("El Artista contiene albumes y/o canciones activas!", " Error!!", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void onClickNewArtista() {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlg').show();");
    }

    public void listenerFile(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();
        UploadedFile file = event.getFile();
        this.setUrlImg(file);
        if (file != null) {
            if ((file.getFileName().toLowerCase().contains(".jpg")
                    || file.getFileName().toLowerCase().contains(".png")
                    || file.getFileName().toLowerCase().contains(".bmp")
                    || file.getFileName().toLowerCase().contains(".gif"))) {

                this.fileName = file.getFileName();
                this.setFileName(this.fileName);
            } else {
                this.Alerta("Debe seleccionar una imagen valida", " ATENCION!!", FacesMessage.SEVERITY_WARN);
            }
        } else {
            this.Alerta("Debe seleccionar una imagen!", " ATENCION!!", FacesMessage.SEVERITY_WARN);
        }
    }

    public void crearArtista() {
        PrimeFaces current = PrimeFaces.current();

        if (this.fileName.equals("") || this.fileName == null) {
            this.Alerta("Debe seleccionar una imagen!", " ATENCION!!", FacesMessage.SEVERITY_WARN);
            return;
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext scontext = (ServletContext) context.getExternalContext().getContext();
            String savePath = scontext.getRealPath("/resources/images/");

            Artista newArtista = new Artista();
            newArtista.setNombre(this.getNombre());
            newArtista.setDescripcion(this.getDescripcion());
            
            String url = "";
            try {
                InputStream input = this.getUrlImg().getInputStream();

                Path folder = Paths.get(savePath);
                String filename = FilenameUtils.getBaseName(this.getFileName());
                String extension = FilenameUtils.getExtension(this.getFileName());
                Path file = Files.createTempFile(folder, filename + "-", "." + extension);
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                url = file.getFileName().toString();

            } catch (IOException ex) {
            }
            url = "../../resources/images/" + url;
            newArtista.setUrl_img(url);
            if (new ArtistaService().CrearArtista(newArtista)) {
                current.executeScript("PF('dlg').hide();");
                this.Alerta("Artista creado satisfactoriamente", " EXITO!!", FacesMessage.SEVERITY_INFO);

            } else {
                this.Alerta("No se pudo crear el Artista", " Error!!", FacesMessage.SEVERITY_FATAL);
            }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UploadedFile getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(UploadedFile urlImg) {
        this.urlImg = urlImg;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
