package com.ladsoft.bakingapp.di.module;
        
import com.ladsoft.bakingapp.activity.MainActivity;
import com.ladsoft.bakingapp.activity.RecipeActivity;
import com.ladsoft.bakingapp.activity.RecipeStepActivity;
import com.ladsoft.bakingapp.activity.di.MainActivityModule;
import com.ladsoft.bakingapp.activity.di.RecipeActivityModule;
import com.ladsoft.bakingapp.activity.di.RecipeStepActivityModule;
import com.ladsoft.bakingapp.fragment.di.RecipeFragmentBuilder;
import com.ladsoft.bakingapp.fragment.di.RecipesFragmentBuilder;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = { MainActivityModule.class, RecipesFragmentBuilder.class })
    abstract MainActivity providesMainActivity();

    @ContributesAndroidInjector(modules = { RecipeActivityModule.class, RecipeFragmentBuilder.class })
    abstract RecipeActivity providesRecipesActivity();

    @ContributesAndroidInjector(modules = RecipeStepActivityModule.class)
    abstract RecipeStepActivity providesRecipeStepActivity();
}