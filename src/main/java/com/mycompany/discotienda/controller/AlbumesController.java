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
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
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
import javax.servlet.ServletContext;
import javax.validation.constraints.Size;
import org.primefaces.PrimeFaces;
import org.primefaces.component.fileupload.FileUploadHandler;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

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
    /**
     * variables para la creacion de un album
     */
    
    private String nombre;
    private double precio;
    private String duracion;
    private String anio;
    private UploadedFile urlImg;
    private String fileName;
    private int newId_Artista;
    /**
     * variables para la creacion de cancion
     */

    private String nombreCancion;
    private double precioCancion;
    private String duracionCancion;
    private int idAlbum;

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
        this.setListadoCanciones(new AdminService().BuscarCanciones_XAlbum(id_album));
        this.listadoCanciones = this.getListadoCanciones();
                
        this.idAlbum = id_album;
        this.setIdAlbum(this.idAlbum);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlg').show();");

    }

    public void onClickNewAlbum(int id_artistA) {
        PrimeFaces current = PrimeFaces.current();
        this.newId_Artista = id_artistA;
        this.setNewId_Artista(this.newId_Artista);              
        current.executeScript("PF('dlgNewAlbum').show();");
    }

    public void onClickNewCancion(int id_artistA) {
        PrimeFaces current = PrimeFaces.current();
        this.newId_Artista = id_artistA;
        this.setNewId_Artista(this.newId_Artista);        
        current.executeScript("PF('dlgNewCanciones').show();");
    }

    public void eliminarCancion(String rowKey) {
        if (new AdminService().EliminarCancion(rowKey)) {
            this.Alerta("Cancion Eliminada!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);

        } else {
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

    public void onClickElimAlbum(String rowKey) {
        if (new AdminService().EliminarAlbum(rowKey)) {
            this.Alerta("Album Eliminado!", " ATENCIÓN", FacesMessage.SEVERITY_WARN);
        } else {
            this.Alerta("El album contiene canciones activas!", " Error!!", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void crearAlbum() {
        PrimeFaces current = PrimeFaces.current();

        if (this.fileName.equals("") || this.fileName == null) {
            this.Alerta("Debe seleccionar una imagen!", " ATENCION!!", FacesMessage.SEVERITY_WARN);
            return;
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext scontext = (ServletContext) context.getExternalContext().getContext();
            String savePath = scontext.getRealPath("/resources/images/");

            Album newAlbum = new Album();
            newAlbum.setNombre(this.getNombre());

            newAlbum.setPrecio(this.getPrecio());
            newAlbum.setDuracion(Integer.parseInt(this.getDuracion()));
            newAlbum.setAnio(Integer.parseInt(this.getAnio()));
            newAlbum.setId_artista(this.getId_Artista());

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
            newAlbum.setUrl_img(url);
            if (new AdminService().CrearAlbum(newAlbum)) {
                current.executeScript("PF('dlgNewAlbum').hide();");
                this.Alerta("Album creado satisfactoriamente", " EXITO!!", FacesMessage.SEVERITY_INFO);

            } else {
                this.Alerta("No se pudo crear el album", " Error!!", FacesMessage.SEVERITY_FATAL);
            }

        }

    }

    public void crearCancion() {
        PrimeFaces current = PrimeFaces.current();
        
        Cancion newCancion = new Cancion();
        newCancion.setNombre(this.getNombreCancion());
       // newCancion.setPrecio(this.getPrecioCancion());
        newCancion.setDuracion(this.getDuracionCancion());
        newCancion.setId_album(this.getIdAlbum());
        newCancion.setId_artista(this.getId_Artista());       
        if (new AdminService().CrearCancion(newCancion)) {
            current.executeScript("PF('dlgNewCanciones').hide();");
            this.Alerta("Cancion creada satisfactoriamente", " EXITO!!", FacesMessage.SEVERITY_INFO);
        } else {
            this.Alerta("No se pudo crear la Cancion", " ERROR!!", FacesMessage.SEVERITY_FATAL);
        }
        
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
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

    public int getNewId_Artista() {
        return newId_Artista;
    }

    public void setNewId_Artista(int newId_Artista) {
        this.newId_Artista = newId_Artista;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public double getPrecioCancion() {
        return precioCancion;
    }

    public void setPrecioCancion(double precioCancion) {
        this.precioCancion = precioCancion;
    }

    public String getDuracionCancion() {
        return duracionCancion;
    }

    public void setDuracionCancion(String duracionCancion) {
        this.duracionCancion = duracionCancion;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

}
