package icesi.i2t.cookit.model;

public class Usuario {

    private String name;
    private String last_name;
    private String email;
    private String user_id;

    public Usuario() {
    }

    public Usuario(String name, String last_name, String email, String user_id) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
