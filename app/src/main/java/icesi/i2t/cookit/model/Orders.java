package icesi.i2t.cookit.model;

import java.util.HashSet;

public class Orders {

    public static final String PENDIENTE = "Orden pendiente";
    public static final String ACEPTADA = "Orden aceptada";
    public static final String COMPRADO = "Orden comprado";
    public static final String LLEGO = "Llego la orden";
    public static final String ENTREGADO = "Orden entregada";

    public String id;
    public String nombreReceta;
    public HashSet<String> ingedientes;

    public Orders() {
        ingedientes = new HashSet<>();
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

    public HashSet<String> getIngedientes() {
        return ingedientes;
    }

    public void setIngedientes(HashSet<String> ingedientes) {
        this.ingedientes = ingedientes;
    }
}
