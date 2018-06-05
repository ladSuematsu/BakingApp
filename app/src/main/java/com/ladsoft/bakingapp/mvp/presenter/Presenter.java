package com.ladsoft.bakingapp.mvp.presenter;

import java.lang.ref.WeakReference;

public abstract class Presenter<V> {
    private WeakReference<V> view;

    public void attachView(V view) {
        this.view = new WeakReference<>(view);
    }

    public void detachView() {
        this.view = null;
    }

    protected V getView() {
        return this.view.get();
    }

    protected boolean isViewAttached() {
        return this.view != null && this.view.get() != null;
    }
}

