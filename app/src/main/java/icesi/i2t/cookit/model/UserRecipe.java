package icesi.i2t.cookit.model;

public class UserRecipe {

    public static final int CREATE = 0;
    public static final int SEARCH = 1;
    public static final int LIKE = 2;

    private User user;
    private Recipe recipe;
    private int type;
    private double weight;

    public UserRecipe() {
    }

    @Deprecated
    public UserRecipe(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public UserRecipe(User user, Recipe recipe, int type) {
        this.user = user;
        this.recipe = recipe;
        this.type = type;
        weight = 0;
    }

    public UserRecipe(User user, Recipe recipe, int type, double weight) {
        this.user = user;
        this.recipe = recipe;
        this.type = type;
        this.weight = weight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
