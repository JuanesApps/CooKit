package icesi.i2t.cookit.model;

import android.support.annotation.NonNull;

public class Like{

    private String id_var;
    private String id;
    private String id_lkusr;

    public Like() {
    }

    public Like(String id_var) {
        this.id_var = id_var;
    }

    public String getId_var() {
        return id_var;
    }

    public void setId_var(String id_var) {
        this.id_var = id_var;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Like){
            Like c = (Like) obj;
            return getId_var().equals(c.getId_var());
        }
        return false;
    }

    public String getId_lkusr() {
        return id_lkusr;
    }

    public void setId_lkusr(String id_lkusr) {
        this.id_lkusr = id_lkusr;
    }
}
