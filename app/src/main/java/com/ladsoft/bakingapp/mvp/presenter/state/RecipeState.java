package com.ladsoft.bakingapp.mvp.presenter.state;

import android.os.Bundle;

import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.mvp.RecipeMvp;

import java.io.Serializable;
import java.util.HashMap;

public class RecipeState implements RecipeMvp.StateContainer {
    private final SessionManager sessionManager;
    private long recipeId;

    public RecipeState(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void load(Bundle bundle) {
        recipeId = bundle.getLong(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, 0L);
        sessionManager.setLastSelectedReceiptId(recipeId);
    }

    public void load(Serializable serializable) {
        HashMap<String, Object> stateMap = (HashMap<String, Object>) serializable;

        recipeId = stateMap.containsKey(RecipeMvp.StateContainer.EXTRA_RECIPE_ID)
                                        ? (long) stateMap.get(RecipeMvp.StateContainer.EXTRA_RECIPE_ID)
                                        : 0L;
    }

    @Override
    public long getRecipeId() {
        return recipeId;
    }

    @Override
    public HashMap<String, Object> getStateMap() {
        HashMap<String, Object> stateMap = new HashMap<>();
        stateMap.put(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, recipeId);

        return stateMap;
    }
}
