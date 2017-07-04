package com.ladsoft.bakingapp.mvp.presenter

abstract class Presenter<V> {

    open var view: V? = null

    open fun attachView(view: V) {
        this.view = view
    }

    open fun detachView() {
        this.view = null
    }

    fun isViewAttached(): Boolean {
        return this.view != null
    }
}

