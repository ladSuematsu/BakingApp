package com.ladsoft.bakingapp.data.repository;

import com.ladsoft.bakingapp.data.network.api.BakingAppApiModule;
import com.ladsoft.bakingapp.data.network.entity.RecipePayload;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.translator.RecipeTranslator;
import com.ladsoft.bakingapp.translator.Translator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class RecipeRepository {
    BakingAppApiModule apiModule;

    public RecipeRepository(BakingAppApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public List<Recipe> recipes() throws Exception {
        List<Recipe> recipes = new ArrayList<>();

        Response<List<RecipePayload>> response = apiModule.getApi().recipes().execute();

        if (!response.isSuccessful()) {
            throw new Exception("Request unsuccessful. HTTP error" + response.code());
        } else {
            List<RecipePayload> recipePayloads = response.body();

            Translator<RecipePayload, Recipe> translator = new RecipeTranslator();
            if (recipePayloads != null) {
                for (RecipePayload recipePayload : recipePayloads) {
                    recipes.add(translator.translate(recipePayload));
                }
            }
        }

        return recipes;
    }
}
