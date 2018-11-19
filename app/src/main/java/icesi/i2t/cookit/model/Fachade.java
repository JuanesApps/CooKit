package icesi.i2t.cookit.model;

import android.provider.MediaStore;

import java.util.ArrayList;

public class Fachade {

    private ArrayList<User> users;
    private ArrayList<Recipe> recipes;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<UserRecipe> userRecipes;
    private ArrayList<RecipeIngredient> recipeIngredients;
    private Analytics analytics;

    public Fachade() {
        users = new ArrayList<>();
        recipes = new ArrayList<>();
        ingredients = new ArrayList<>();
        userRecipes = new ArrayList<>();
        recipeIngredients = new ArrayList<>();
        analytics = new Analytics(this);
    }

    public boolean createIngredient(String name, String description) {
        Ingredient ingredient = new Ingredient(name, description);
        for (Ingredient ingredientTemp : ingredients) {
            if (ingredientTemp.getDescription().equals(ingredient.getDescription()))
                return false;
        }
        ingredients.add(ingredient);
        return true;
    }

    public boolean createUser(String name, String last_name, String email, String user_id) {
        for (User u : users) {
            if (u.getEmail().equals(email) || u.getUser_id().equals(user_id)) {
                return false;
            }
        }
        User user = new User(name, last_name, email, user_id);
        users.add(user);
        return true;
    }

    public boolean createRecipe(ArrayList<Ingredient> ingredients, User user, String name, String description) {
        //ArrayList<RecipeIngredient> ingredients, UserRecipe creator, String name, String description
        int nRecipes = user.getCreations().size();

        // Create recipe
        Recipe recipe = new Recipe(null, null, name, description);
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (Ingredient ing : ingredients) {
            recipeIngredients.add(new RecipeIngredient(recipe, ing, "Something"));
        }
        recipe.setIngredients(recipeIngredients);

        // Create
        UserRecipe userRecipeLocal = new UserRecipe(user, recipe, UserRecipe.CREATE);
        recipe.setCreator(userRecipeLocal);
        user.getCreations().add(userRecipeLocal);
        userRecipes.add(userRecipeLocal);
        analytics.addUserRecipe(userRecipeLocal);
        if (user.getCreations().size() == nRecipes + 1)
            return true;
        return false;
    }

    public boolean createLike(User user, Recipe recipe) {
        UserRecipe userRecipeLocal = new UserRecipe(user, recipe, UserRecipe.LIKE);
        userRecipes.add(userRecipeLocal);
        user.getLikes().add(userRecipeLocal);
        recipe.getLikes().add(userRecipeLocal);
        analytics.addUserRecipe(userRecipeLocal);
        return true;
    }

    public boolean createSearch(User user, Recipe recipe) {
        UserRecipe userRecipeLocal = new UserRecipe(user, recipe, UserRecipe.SEARCH);
        userRecipes.add(userRecipeLocal);
        user.getLikes().add(userRecipeLocal);

        recipe.getLikes().add(userRecipeLocal);
        analytics.addUserRecipe(userRecipeLocal);
        return true;
    }

    public ArrayList<Recipe> recommend(User user) {
        return analytics.recommend(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<UserRecipe> getUserRecipes() {
        return userRecipes;
    }

    public void setUserRecipes(ArrayList<UserRecipe> userRecipes) {
        this.userRecipes = userRecipes;
    }

    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public Analytics getAnalytics() {
        return analytics;
    }

    public void setAnalytics(Analytics analytics) {
        this.analytics = analytics;
    }
}
