/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ladsoft.bakingapp.mvp

import android.support.test.espresso.IdlingResource
import android.util.Log

/**
 * Contains a static reference to [IdlingResource], only available in the 'mock' build type.
 */
object EspressoIdlingResource {
    val LOG_TAG = EspressoIdlingResource::class.simpleName
    val RESOURCE = "GLOBAL"
    val countingIdlingResource = CustomCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
        Log.d(LOG_TAG, "Counting idling resource increment: " + countingIdlingResource.counter)
    }

    fun decrement() {
        countingIdlingResource.decrement()
        Log.d(LOG_TAG, "Counting idling resource decrement: " + countingIdlingResource.counter)
    }
}
