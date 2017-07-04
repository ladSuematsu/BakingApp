package com.ladsoft.bakingapp.translator


interface Translator<S, T> {
    fun translate(source: S): T
}
