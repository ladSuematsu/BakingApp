/**
 * Original source: https://github.com/googlecodelabs/android-testing/blob/master/app/src/main/java/com/example/android/testing/notes/util/EspressoIdlingResource.java
 */

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

package com.ladsoft.bakingapp.mvp;

import android.util.Log;

public abstract class EspressoIdlingResource {
    public static final String LOG_TAG = EspressoIdlingResource.class.getSimpleName();
    public static final String RESOURCE = "GLOBAL";
    public static final CustomCountingIdlingResource countingIdlingResource = new CustomCountingIdlingResource(RESOURCE);

    static public void increment() {
        countingIdlingResource.increment();
        Log.d(LOG_TAG, "Counting idling resource increment: " + countingIdlingResource.getCounter());
    }

    static public void decrement() {
        countingIdlingResource.decrement();
        Log.d(LOG_TAG, "Counting idling resource decrement: " + countingIdlingResource.getCounter());
    }
}
