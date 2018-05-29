package com.ladsoft.bakingapp.mvp.presenter.state

import android.os.Bundle
import com.ladsoft.bakingapp.manager.SessionManager
import com.ladsoft.bakingapp.mvp.RecipeMvp
import java.io.Serializable


class RecipeState: RecipeMvp.StateContainer {
    override var recipeId : Long = 0L
    val sessionManager: SessionManager

    constructor(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    fun load(bundle: Bundle?) {
        (bundle?.getLong(RecipeMvp.StateContainer.EXTRA_RECIPE_ID) ?: 0L).apply {
            recipeId = this
            sessionManager.setLastSelectedReceiptId(this)
        }
    }

    fun load(serializable: Serializable?) {
        serializable?.apply {
            val stateMap = serializable as HashMap<*, *>
            recipeId = stateMap[RecipeMvp.StateContainer.EXTRA_RECIPE_ID] as Long
        }
    }

    override fun getStateMap(): HashMap<String, Any> {
        val stateMap = HashMap<String, Any>()
        stateMap.put(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, recipeId)

        return stateMap
    }

}
