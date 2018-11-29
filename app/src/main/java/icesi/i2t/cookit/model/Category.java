package icesi.i2t.cookit.model;

import java.util.HashSet;

public class Category {

    private String name;
    private String id;
    private HashSet<String> recipies;

    public Category() {
        recipies = new HashSet<>();
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

    public HashSet<String> getRecipies() {
        return recipies;
    }

    public void setRecipies(HashSet<String> recipies) {
        this.recipies = recipies;
    }
}
