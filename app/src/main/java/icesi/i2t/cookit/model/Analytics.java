package icesi.i2t.cookit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Analytics {

    private Fachade fachade;
    private ArrayList<ArrayList<Double>> recipeMatrix;
    private HashMap<Recipe, Integer> hmRecipeToIndex;
    private HashMap<Integer, Recipe> hmIndexToRecipe;

    public Analytics(Fachade fachade) {
        this.fachade = fachade;
        hmRecipeToIndex = new HashMap<>();
        hmIndexToRecipe = new HashMap<>();
        initRecipeMatrix();
    }

    public void initRecipeMatrix() {
        int nRecipies = fachade.getRecipes().size();
        recipeMatrix = new ArrayList<>(nRecipies);

        //Create matrix with huge values
        for (int i = 0; i < nRecipies; i++) {
            hmRecipeToIndex.put(fachade.getRecipes().get(i), i);
            hmIndexToRecipe.put(i, fachade.getRecipes().get(i));
            recipeMatrix.add(new ArrayList<>(nRecipies));
            for (int j = 0; j < nRecipies; j++) {
                if (i != j)
                    recipeMatrix.get(i).add(Double.MAX_VALUE);
                else
                    recipeMatrix.get(i).add(0.0);
            }
        }

        for (User u : fachade.getUsers()) {

            // List of all recipes related by same User
            ArrayList<Integer> relatedRecipies = new ArrayList<>();
            // Creations
            for (UserRecipe ur : u.getCreations()) {

                // Gets new recipe to interact
                int recipeIndex = hmRecipeToIndex.get(ur.getRecipe());
                // Changes value for all previous relations between recipes
                for (int i = 0; i < relatedRecipies.size(); i++) {
                    if (i != recipeIndex) {

                        //Gets weight value from matrix in position recipieIndex, relatedRecipies.get(i)
                        double newValue = recipeMatrix.get(relatedRecipies.get(i)).get(recipeIndex);

                        // Reduces the value, thus increasing strength of relation
                        if (newValue == Double.MAX_VALUE)
                            newValue = 1000000;
                        else
                            newValue -= 100;

                        //Applies change to the matrix
                        recipeMatrix.get(i).set(recipeIndex, newValue);
                        recipeMatrix.get(recipeIndex).set(i, newValue);
                    }
                }
                relatedRecipies.add(recipeIndex);
            }

            //Likes
            for (UserRecipe ur : u.getLikes()) {

                // Gets new recipe to interact
                int recipeIndex = hmRecipeToIndex.get(ur.getRecipe());
                // Changes value for all previous relations between recipes
                for (int i = 0; i < relatedRecipies.size(); i++) {
                    if (i != recipeIndex) {

                        //Gets weight value from matrix in position recipieIndex, relatedRecipies.get(i)
                        double newValue = recipeMatrix.get(relatedRecipies.get(i)).get(recipeIndex);

                        // Reduces the value, thus increasing strength of relation
                        if (newValue == Double.MAX_VALUE)
                            newValue = 1000000;
                        else
                            newValue -= 150;

                        //Applies change to the matrix
                        recipeMatrix.get(i).set(recipeIndex, newValue);
                        recipeMatrix.get(recipeIndex).set(i, newValue);
                    }
                }
                relatedRecipies.add(recipeIndex);
            }


            //Searches
            for (UserRecipe ur : u.getLikes()) {

                // Gets new recipe to interact
                int recipeIndex = hmRecipeToIndex.get(ur.getRecipe());
                // Changes value for all previous relations between recipes
                for (int i = 0; i < relatedRecipies.size(); i++) {
                    if (i != recipeIndex) {

                        //Gets weight value from matrix in position recipieIndex, relatedRecipies.get(i)
                        double newValue = recipeMatrix.get(relatedRecipies.get(i)).get(recipeIndex);

                        // Reduces the value, thus increasing strength of relation
                        if (newValue == Double.MAX_VALUE)
                            newValue = 1000000;
                        else
                            newValue -= 50;

                        //Applies change to the matrix
                        recipeMatrix.get(i).set(recipeIndex, newValue);
                        recipeMatrix.get(recipeIndex).set(i, newValue);
                    }
                }
                relatedRecipies.add(recipeIndex);
            }
        }
        // Calculates Floyd Warshall
        floydWarshall(recipeMatrix);

    }

    public void floydWarshall(ArrayList<ArrayList<Double>> graph) {

        int i, j, k;
        int V = graph.size();

        for (k = 0; k < V; k++) {

            for (i = 0; i < V; i++) {

                for (j = 0; j < V; j++) {

                    if (graph.get(i).get(j) + graph.get(k).get(j) < graph.get(i).get(j))
                        graph.get(i).set(j, graph.get(i).get(k) + graph.get(j).get(j));
                }
            }
        }
    }

    public ArrayList<Recipe> recommend(User user) {
        ArrayList<recipeWithValue> recommended = new ArrayList<>();
        HashSet<Double> added = new HashSet<>();
        for (UserRecipe userRecipe : user.getCreations()) {
            // Pair
            int index = hmRecipeToIndex.get(userRecipe.getRecipe());
            for (int i = 0; i < recipeMatrix.size(); i++) {
                if (i != index) {
                    double w = recipeMatrix.get(index).get(i);
                    w *= 1.2;
                    Recipe r = hmIndexToRecipe.get(i);
                    recommended.add(new recipeWithValue(w, r));
                    //hmRecipeToIndex.
                }
            }
        }

        for (UserRecipe userRecipe : user.getLikes()) {
            // Pair
            int index = hmRecipeToIndex.get(userRecipe.getRecipe());
            for (int i = 0; i < recipeMatrix.size(); i++) {
                if (i != index) {
                    double w = recipeMatrix.get(index).get(i);
                    w *= 1.1;
                    Recipe r = hmIndexToRecipe.get(i);
                    recommended.add(new recipeWithValue(w, r));
                    //hmRecipeToIndex.
                }
            }
        }

        for (UserRecipe userRecipe : user.getSearches()) {
            // Pair
            int index = hmRecipeToIndex.get(userRecipe.getRecipe());
            for (int i = 0; i < recipeMatrix.size(); i++) {
                if (i != index) {
                    double w = recipeMatrix.get(index).get(i);
                    Recipe r = hmIndexToRecipe.get(i);
                    recommended.add(new recipeWithValue(w, r));
                    //hmRecipeToIndex.
                }
            }
        }

        Collections.sort(recommended);
        ArrayList<Recipe> result = new ArrayList<>();
        for (recipeWithValue rwv : recommended) {
            result.add(rwv.recipe);
        }
        return result;
    }

    public void addUserRecipe(UserRecipe ur) {
        int strengthen = -100;
        switch (ur.getType()) {
            case UserRecipe.CREATE:
                strengthen = 100;
                break;
            case UserRecipe.LIKE:
                strengthen = 150;
                break;
            case UserRecipe.SEARCH:
                strengthen = 50;
                break;
        }
        int index = hmRecipeToIndex.get(ur.getRecipe());
        for (int i = 0; i < recipeMatrix.size(); i++) {
            if (i != index) {
                recipeMatrix.get(index).set(i, recipeMatrix.get(index).get(i) - strengthen);
            }
        }
        floydWarshall(recipeMatrix);
    }

    private class recipeWithValue implements Comparable<recipeWithValue> {
        double weight;
        Recipe recipe;

        public recipeWithValue(double weight, Recipe recipe) {
            this.weight = weight;
            this.recipe = recipe;
        }

        @Override
        public int compareTo(recipeWithValue o) {
            return 0;
        }
    }

}
