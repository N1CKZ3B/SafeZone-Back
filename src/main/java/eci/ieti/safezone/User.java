package eci.ieti.safezone;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean // Indica que esta clase representa una tabla en DynamoDB
public class User {

    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String ciudad;
    private boolean postulado;
    private boolean elegido;
    private String password;

    // Constructor vacío (necesario para DynamoDB)
    public User() {}

    // Getters y Setters
    @DynamoDbPartitionKey // Indica que 'id' es la clave primaria de la tabla
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

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
