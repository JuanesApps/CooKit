package icesi.i2t.cookit.model;

import android.provider.MediaStore;

import java.util.ArrayList;

public class Recipe {

    private ArrayList<RecipeIngredient> ingredients;
    private UserRecipe creator;
    private ArrayList<UserRecipe> likes;
    private ArrayList<UserRecipe> searches;
    private String id;
    private String punctuation;
    private String steps;



    private String name;
    private String description;
    private MediaStore.Video video;


    public Recipe() {
    }

    public Recipe(ArrayList<RecipeIngredient> ingredients, UserRecipe creator, ArrayList<UserRecipe> likes, ArrayList<UserRecipe> searches, String name, String description, MediaStore.Video video) {
        this.ingredients = ingredients;
        this.creator = creator;
        this.likes = likes;
        this.searches = searches;
        this.name = name;
        this.description = description;
        this.video = video;
    }


    public Recipe(ArrayList<RecipeIngredient> ingredients, UserRecipe creator, String name, String description) {
        this.ingredients = ingredients;
        this.creator = creator;
        this.name = name;
        this.description = description;
        likes = new ArrayList<>();
        searches = new ArrayList<>();
        video = null;
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public UserRecipe getCreator() {
        return creator;
    }

    public void setCreator(UserRecipe creator) {
        this.creator = creator;
    }

    public ArrayList<UserRecipe> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<UserRecipe> likes) {
        this.likes = likes;
    }

    public ArrayList<UserRecipe> getSearches() {
        return searches;
    }

    public void setSearches(ArrayList<UserRecipe> searches) {
        this.searches = searches;
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

    public MediaStore.Video getVideo() {
        return video;
    }

    public void setVideo(MediaStore.Video video) {
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(String punctuation) {
        this.punctuation = punctuation;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
