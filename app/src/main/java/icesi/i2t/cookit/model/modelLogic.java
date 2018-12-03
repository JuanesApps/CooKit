package icesi.i2t.cookit.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class modelLogic {

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DataBase dataBase;
    private static modelLogic logic;
    public HashMap<String, User> usuarios;
    public HashMap<String, Recipe> recipeHashMap;
    public HashMap<String, Ingredient> ingredientHashMap;
    public HashMap<String, Ingredient> ingredientName;
    public HashMap<String, Category> categories;
    public HashMap<String, Category> categoriesNames;


    public modelLogic(DataBase dataBase) {
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        this.dataBase = dataBase;
        usuarios = dataBase.getUserList();
        Log.e("-----------+", usuarios.size()+"");
    }

    public modelLogic() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        dataBase = new DataBase(this);
    }

//    public void add(){
//        main.addAllrec(this);
//    }

//    public static modelLogic getInstance(){
//        if (logic == null){
//            logic = new modelLogic();
//        }
//        return logic;
//    }

    public void refreshUsuarios(HashMap<String, User> usuarios){
        this.usuarios = usuarios;
        Log.e("-----------usu", this.usuarios.size()+"");
    }

    public void refreshRecetas(HashMap<String, Recipe> recetas){
        this.recipeHashMap = recetas;
        Log.e("-----------rec", recipeHashMap.size()+"");
    }

    public void  refreshIngredientes(HashMap<String, Ingredient> ingredientHashMap){
        this.ingredientHashMap = ingredientHashMap;

        Log.e("-----------map", ingredientHashMap.size()+"");
    }

    public void  refreshIngredientesName(HashMap<String, Ingredient> ingredientHashMap){
        this.ingredientName = ingredientHashMap;

        Log.e("-----------nam", ingredientHashMap.size()+"");
    }

    public void  refreshCategories(HashMap<String, Category> categoryHashMap){
        this.categories = categoryHashMap;

        Log.e("-----------cat", categories.size()+"");
    }

    public void  refreshCategoriesNames(HashMap<String, Category> categoryHashMap){
        this.categoriesNames = categoryHashMap;

        Log.e("-----------cat", categories.size()+"");
    }

    public String addRecipie(ArrayList<String> pasos, ArrayList<String> ingredients, ArrayList<String> categories, String nombre){

        DatabaseReference recipie_ref = db.getReference().child("recipes").push();
        Recipe rec = new Recipe();
        rec.setId(recipie_ref.getKey());
        rec.setName(nombre);
        rec.setSteps(pasos);
        rec.setU_id(auth.getCurrentUser().getUid());
        HashMap<String, String> ingIds = addIngredients(ingredients, rec.getId());
        rec.setIngredient(ingIds);
        HashMap<String, String> catIds = addCategories(categories, rec.getId());
        rec.setCategories(catIds);
        recipie_ref.setValue(rec);
//        for (String ing: ingIds) {
//            db.getReference().child("recipes").child(rec.getId()).child("ingrediente").push().setValue(ing);
//        }
        db.getReference().child("usuarios").child(auth.getCurrentUser().getUid()).child("created").child(rec.getId()).setValue(rec.getId());
        return rec.getId();
    }

    private HashMap<String, String> addIngredients(ArrayList<String> ingredients, String recipieId){
        DatabaseReference ingredientes_ref = db.getReference().child("ingredients");
        HashMap<String, String> ingIds = new HashMap<>();
        for (int i = 0; i < ingredients.size(); i++) {
            if(ingredientName.containsKey(ingredients.get(i))){
                Log.e("-----------a", "Se ejecuta");
                Ingredient ing = ingredientName.get(ingredients.get(i));
                Log.e("-----------a", ing.getId());
                ingredientes_ref.child(ing.getId()).child("recipes").child(recipieId).setValue(recipieId);
                ingIds.put(ing.getId(), ing.getId());
            }
            else{
                Ingredient ing = new Ingredient();
                ing.setName(ingredients.get(i));
                ing.getRecipes().put(recipieId, recipieId);
                DatabaseReference br = ingredientes_ref.push();
                ing.setId(br.getKey());
                br.setValue(ing);
                ingIds.put(ing.getId(), ing.getId());
            }
        }
        return ingIds;
    }

    private HashMap<String, String> addCategories(ArrayList<String> cats, String recipieId){
        DatabaseReference categories_ref = db.getReference().child("categories");
        HashMap<String, String> catIds = new HashMap<>();
        for (int i = 0; i < cats.size(); i++) {
            if(categoriesNames.containsKey(cats.get(i))){
                Category cat = categoriesNames.get(cats.get(i));
                categories_ref.child(cat.getId()).child("recipes").child(recipieId).setValue(recipieId);
                catIds.put(cat.getId(), cat.getId());
            }
            else{
                Category ing = new Category();
                ing.setName(cats.get(i));
                ing.getRecipes().put(recipieId, recipieId);
                DatabaseReference br = categories_ref.push();
                ing.setId(br.getKey());
                br.setValue(ing);
                catIds.put(ing.getId(), ing.getId());
            }
        }
        return catIds;
    }

    public boolean doLike(String recid){
        DatabaseReference user_ref = db.getReference().child("usuarios");
        DatabaseReference recipie_ref = db.getReference().child("recipes");
        User user = usuarios.get(auth.getUid());
        Recipe rec = recipeHashMap.get(recid);
        if (user.getLiked().containsValue(recid)){
            user_ref.child(user.getUser_id()).child("liked").child(recid).removeValue();
            recipie_ref.child(recid).child("likedby").child(user.getUser_id()).removeValue();
            return false;
        } else{
            user_ref.child(user.getUser_id()).child("liked").push().setValue(recid);
            recipie_ref.child(recid).child("likedby").push().setValue(user.getUser_id());
            return true;
        }

    }

    public ArrayList<Recipe> getFavs(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        HashMap<String, Recipe> recipeHashMap = dataBase.getRecipeList();
        HashMap<String, User> users = dataBase.getUserList();
        HashMap<String, Ingredient> ingredients = dataBase.getIngedientsList();
        User user = users.get(auth.getCurrentUser().getUid());
        HashMap<String, Like> liked = user.getLiked();
        for (Like id: liked.values()) {
            Recipe recipe = recipeHashMap.get(id);
            ArrayList<Ingredient> ings = new ArrayList<>();
            for (String ing: recipe.getIngredient().values()) {
                ings.add(ingredients.get(ing));
            }
            recipe.setIngre(ings);
            recipes.add(recipe);
        }

        return recipes;
    }

    public ArrayList<Recipe> getAllRecipies(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipeHashMap = dataBase.getRecipeList();
        ingredientHashMap = dataBase.getIngedientsList();
        Log.e("+++++++++++++", recipeHashMap.size()+"");
        for (Recipe rec: recipeHashMap.values()) {
            ArrayList<Ingredient> ings = new ArrayList<>();
            for (String ing: rec.getIngredient().values()) {
                ings.add(ingredientHashMap.get(ing));
            }
            rec.setIngre(ings);
            recipes.add(rec);
        }

        return recipes;
    }

    public void addPrices() {
        DatabaseReference ing_ref = db.getReference().child("ingredients");
        for (Ingredient ing: ingredientHashMap.values()) {
            Random rand = new Random();
            int num = rand.nextInt(50)+1;
            String cost = "$" + num + ".000";
            ing_ref.child(ing.getId()).child("cost").setValue(cost);
        }
    }

    public void wipeIngsAndCats(){
        DatabaseReference ing_ref = db.getReference().child("ingredients");
        DatabaseReference cat_ref = db.getReference().child("categories");
        HashMap<String, Ingredient> map1 = new HashMap<>();
        for (Ingredient ing: ingredientHashMap.values()) {
            if (!map1.containsKey(ing.getName())){
                ing.setRecipes(new HashMap<>());
                map1.put(ing.getName(), ing);
            }
        }
        ing_ref.removeValue();
        for (Ingredient ing: map1.values()) {
            ing_ref.child(ing.getId()).setValue(ing);
        }
        HashMap<String, Category> map2 = new HashMap<>();
        for (Category cat: categories.values()) {
            if (!map2.containsKey(cat.getName())){
                cat.setRecipes(new HashMap<>());
                map2.put(cat.getName(), cat);
            }
        }
        cat_ref.removeValue();
        for (Category cat: map2.values()) {
            cat_ref.child(cat.getId()).setValue(cat);
        }

    }

    public void refresh(){
        dataBase.getUserList();
        dataBase.getIngedientsList();
        dataBase.getRecipeList();
        dataBase.getIngedientsListByName();
        dataBase.getCategoriesList();
        dataBase.getCategoriesListName();
    }

}
