package icesi.i2t.cookit.model;

import java.util.HashMap;

public class Recipie {

    private String id;
    private String title;
    private String punctuation;
    private HashMap<String, String> likes;
    private String ingedients;
    private String recomendedUtencils;
    private String steps;
    private String description;

    public Recipie() {
        likes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(String punctuation) {
        this.punctuation = punctuation;
    }

    public HashMap<String, String> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, String> likes) {
        this.likes = likes;
    }

    public String getIngedients() {
        return ingedients;
    }

    public void setIngedients(String ingedients) {
        this.ingedients = ingedients;
    }

    public String getRecomendedUtencils() {
        return recomendedUtencils;
    }

    public void setRecomendedUtencils(String recomendedUtencils) {
        this.recomendedUtencils = recomendedUtencils;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
