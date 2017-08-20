package com.ladsoft.bakingapp

import com.ladsoft.bakingapp.data.repository.RecipeRepository
import org.junit.Test
import kotlin.test.assertFalse


class ExampleKotlinUnitTest {

    @Test
    fun recipeRepositoryTest() {
        val recipes = RecipeRepository.recipes
        assertFalse(recipes.isEmpty())
    }
}
