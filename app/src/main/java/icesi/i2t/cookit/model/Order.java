package icesi.i2t.cookit.model;

import java.util.HashSet;

public class Order {

    public static final String PENDIENTE = "Orden pendiente";
    public static final String ACEPTADA = "Orden aceptada";
    public static final String COMPRADO = "Orden comprado";
    public static final String LLEGO = "Llego la orden";
    public static final String ENTREGADO = "Orden entregada";

    public String id;
    public String nombreReceta;
    public String direccion;
    public String idReceta;
    public String idUsuario;
    public String idCopmrador;
    private String estado;
    public int cost;

    public Order() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(String idReceta) {
        this.idReceta = idReceta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCopmrador() {
        return idCopmrador;
    }

    public void setIdCopmrador(String idCopmrador) {
        this.idCopmrador = idCopmrador;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
