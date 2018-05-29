package com.ladsoft.bakingapp.translator;

import com.ladsoft.bakingapp.data.network.entity.IngredientPayload;
import com.ladsoft.bakingapp.data.network.entity.RecipePayload;
import com.ladsoft.bakingapp.data.network.entity.StepPayload;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeTranslator implements Translator<RecipePayload, Recipe> {
    @Override
    public Recipe translate(RecipePayload source) {
        IngredientTranslator ingredientTranslator = new IngredientTranslator(source.getId());
        StepTranslator stepTranslator = new StepTranslator(source.getId());

        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps = new ArrayList<>();

        List<IngredientPayload> ingredientsPayload = source.getIngredients();
        if (ingredientsPayload != null) {
            long incrementalId = 0;
            for (IngredientPayload ingredientPayload : ingredientsPayload) {
                ingredientPayload.setId(++incrementalId);
                ingredients.add(ingredientTranslator.translate(ingredientPayload));
            }
        }

        List<StepPayload> stepsPayload = source.getSteps();
        if (stepsPayload != null) {
            for (StepPayload stepPayload : stepsPayload) {
                steps.add(stepTranslator.translate(stepPayload));
            }
        }

        return new Recipe(source.getId(),
                        source.getName() != null ? source.getName() : "",
                        source.getServings(),
                        source.getImageUrl() != null ? source.getImageUrl() : "",
                        ingredients,
                        steps);
    }
}
