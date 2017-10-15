package com.ladsoft.bakingapp.mvp.presenter.state

import android.os.Bundle
import com.ladsoft.bakingapp.mvp.RecipeMvp
import java.io.Serializable


class RecipeState: RecipeMvp.StateContainer {
    override var recipeId : Long = 0L

    constructor(bundle: Bundle?) {
        recipeId = bundle?.getLong(EXTRA_RECIPE_ID) ?: 0L
    }

    constructor(serializable: Serializable?) {
        if (serializable != null) {
            val stateMap = serializable as HashMap<*, *>
            recipeId = stateMap[EXTRA_RECIPE_ID] as Long
        }
    }

    override fun getStateMap(): HashMap<String, Any> {
        val stateMap = HashMap<String, Any>()
        stateMap.put(EXTRA_RECIPE_ID, recipeId)

        return stateMap
    }

}
