package icesi.i2t.cookit.model;

import java.util.HashMap;

public class Category {

    private String name;
    private String id;
    private HashMap<String, String> recipes;

    public Category() {
        recipes = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, String> getRecipes() {
        return recipes;
    }

    public void setRecipes(HashMap<String, String> recipes) {
        this.recipes = recipes;
    }
}
