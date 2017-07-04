package com.ladsoft.bakingapp

import com.ladsoft.bakingapp.data.repository.RecipeRepository
import org.junit.Test
import kotlin.test.assertFalse


class ExampleKotlinUnitTest {
    private val recipeRepository = RecipeRepository.instance

    @Test
    fun recipeRepositoryTest() {
        val recipes = recipeRepository.recipes
        assertFalse(recipes.isEmpty())
    }
}
