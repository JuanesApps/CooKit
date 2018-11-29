package icesi.i2t.cookit.model;

import java.util.HashSet;

public class Ingredient {

    private String name;
    private String id;
    private HashSet<String> recipies;
    private String description;


    public Ingredient(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Ingredient() {
        recipies = new HashSet<>();
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

    public HashSet<String> getRecipies() {
        return recipies;
    }

    public void setRecipies(HashSet<String> recipies) {
        this.recipies = recipies;
    }
}
