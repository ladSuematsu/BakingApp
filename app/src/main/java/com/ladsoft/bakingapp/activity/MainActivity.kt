package com.ladsoft.bakingapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.adapter.RecipesAdapter
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.fragment.RecipesFragment
import com.ladsoft.bakingapp.mvp.RecipeMvp


class MainActivity : AppCompatActivity() {
    @JvmField @BindView(R.id.content)
    var content: FrameLayout? = null

    private var recipesFragment: RecipesFragment? = null

    private val listener = object : RecipesAdapter.Listener {
        override fun onItemClickListener(recipe: Recipe) {
            val intent = Intent(this@MainActivity, RecipeActivity::class.java)
            intent.putExtra(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, recipe.id)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setupFragments(savedInstanceState)
    }

    private fun setupFragments(savedInstanceState: Bundle?) {
        val fragmentManager = supportFragmentManager

        if (savedInstanceState != null) {
            recipesFragment = fragmentManager.findFragmentById(content!!.id) as RecipesFragment
        } else {
            recipesFragment = RecipesFragment.newInstance()
            fragmentManager.beginTransaction()
                    .replace(content!!.id, recipesFragment, RecipesFragment.TAG)
                    .commitNow()
        }

        recipesFragment?.setRecipeAdapterListener(listener)
    }
}
