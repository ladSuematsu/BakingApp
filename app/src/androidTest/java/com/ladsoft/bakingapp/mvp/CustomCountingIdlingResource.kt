package com.ladsoft.bakingapp.mvp

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger


class CustomCountingIdlingResource(val resourceName: String): IdlingResource {
    internal val counter = AtomicInteger(0)
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = resourceName

    override fun isIdleNow(): Boolean = counter.get() == 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val counterValue = counter.decrementAndGet()

        if (counterValue == 0) {
            resourceCallback?.onTransitionToIdle()
        }

        if (counterValue < 0) {
            throw IllegalArgumentException("Counter has been corrupted")
        }
    }

}