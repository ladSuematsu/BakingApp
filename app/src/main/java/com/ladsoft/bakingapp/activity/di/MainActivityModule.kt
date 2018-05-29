package com.ladsoft.bakingapp.activity.di

import com.ladsoft.bakingapp.activity.MainActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {
    @Binds
    abstract fun providesView(view: MainActivity): MainActivity
}