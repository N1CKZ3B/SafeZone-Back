package eci.ieti.safezone;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") // Indica que esta clase representa la colección "users"
public class User {

    @Id
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String ciudad;
    private boolean postulado;
    private boolean elegido;

    // Constructor vacío (necesario para Spring)
    public User() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public boolean isPostulado() { return postulado; }
    public void setPostulado(boolean postulado) { this.postulado = postulado; }

    public boolean isElegido() { return elegido; }
    public void setElegido(boolean elegido) { this.elegido = elegido; }
}
