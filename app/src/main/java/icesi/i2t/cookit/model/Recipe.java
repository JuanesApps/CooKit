package icesi.i2t.cookit.model;

import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Recipe {

    private ArrayList<RecipeIngredient> ingredients;
    private ArrayList<Ingredient> ingre;
    private UserRecipe creator;
    private ArrayList<UserRecipe> likes;
    private ArrayList<UserRecipe> searches;
    private String id;
    private String punctuation;
    private ArrayList<String> steps;
    private String u_id;

    private HashMap<String, Like> likedby;
    private HashMap<String, String> searchedby;
    private HashMap<String, String> ingredient;
    private HashMap<String, String> categories;

    private String name;
    private String description;
    private MediaStore.Video video;


    public Recipe() {
        likedby = new HashMap<>();
        searchedby = new HashMap<>();
        ingredient = new HashMap<>();
        categories = new HashMap<>();
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

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public HashMap<String, Like> getLikedby() {
        return likedby;
    }

    public void setLikedby(HashMap<String, Like> likedby) {
        if (likedby==null){
            likedby = new HashMap<>();
        }
        this.likedby = likedby;
    }

    public HashMap<String, String> getSearchedby() {
        return searchedby;
    }

    public void setSearchedby(HashMap<String, String> searchedby) {
        this.searchedby = searchedby;
    }

    public HashMap<String, String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(HashMap<String, String> ingredient) {
        this.ingredient = ingredient;
    }

    public HashMap<String, String> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, String> categories) {
        this.categories = categories;
    }

    public ArrayList<Ingredient> getIngre() {
        return ingre;
    }

    public void setIngre(ArrayList<Ingredient> ingre) {
        this.ingre = ingre;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Recipe){
            Recipe c = (Recipe) obj;
            return getId().equals(c.getId());
        }
        return false;
    }

}
