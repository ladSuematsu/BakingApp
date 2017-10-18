package com.ladsoft.bakingapp.mvp.presenter.state

import android.os.Bundle
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.manager.SessionManager
import com.ladsoft.bakingapp.mvp.RecipeMvp
import java.io.Serializable
import javax.inject.Inject


class RecipeState: RecipeMvp.StateContainer {
    override var recipeId : Long = 0L
    @Inject lateinit var sessionManager: SessionManager

    init {
        BakingAppApplication.appComponent.inject(this)
    }

    constructor(bundle: Bundle?) {
        (bundle?.getLong(EXTRA_RECIPE_ID) ?: 0L).apply {
            recipeId = this
            sessionManager.setLastSelectedReceiptId(this)
        }
    }

    constructor(serializable: Serializable?) {
        serializable?.apply {
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
