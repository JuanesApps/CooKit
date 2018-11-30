package icesi.i2t.cookit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class User {

    private String name;
    private String last_name;
    private String email;
    private String user_id;



    private HashMap<String, String> created;
    private HashMap<String, Like> liked;
    private HashMap<String, String> searchHistory;
    private HashMap<String, String> orders;

    private ArrayList<UserRecipe> creations;
    private ArrayList<UserRecipe> likes;
    private ArrayList<UserRecipe> searches;

    public User() {
        orders = new HashMap<>();
        created = new HashMap<>();
        searchHistory = new HashMap<>();
        liked = new HashMap<>();
    }

    public User(String name, String last_name, String email, String user_id) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.user_id = user_id;

        creations = new ArrayList<>();
        likes = new ArrayList<>();
        searches = new ArrayList<>();
    }

    public User(String name, String last_name, String email, String user_id, ArrayList<UserRecipe> creations, ArrayList<UserRecipe> likes, ArrayList<UserRecipe> searches) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.user_id = user_id;
        this.creations = creations;
        this.likes = likes;
        this.searches = searches;
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

    public ArrayList<UserRecipe> getCreations() {
        return creations;
    }

    public void setCreations(ArrayList<UserRecipe> creations) {
        this.creations = creations;
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


    public HashMap<String, String> getCreated() {
        return created;
    }

    public void setCreated(HashMap<String, String> created) {
        this.created = created;
    }

    public HashMap<String, Like> getLiked() {
        return liked;
    }

    public void setLiked(HashMap<String, Like> liked) {
        this.liked = liked;
    }

    public HashMap<String, String> getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(HashMap<String, String> searchHistory) {
        this.searchHistory = searchHistory;
    }

    public HashMap<String, String> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<String, String> orders) {
        this.orders = orders;
    }
}
