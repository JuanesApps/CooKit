package icesi.i2t.cookit.model;

import android.support.annotation.NonNull;

import java.util.HashMap;

public class Ingredient implements Comparable<Ingredient>{

    private String name;
    private String id;
    private HashMap<String, String> recipes;
    private String description;


    public Ingredient(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Ingredient() {
        recipes = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int compareTo(@NonNull Ingredient o) {
        return name.compareTo(o.name);
    }
}
