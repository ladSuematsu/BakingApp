package com.ladsoft.bakingapp.util


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

object UiUtils {

    fun hideStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
        }
    }

    fun hideSoftInput(view: View) {
        val inputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSnackbar(view: View, message: String, actionLabel: String?, duration: Int, listener: View.OnClickListener?) {
        val snackbar = Snackbar.make(view, message, duration)

        val snackbarText = snackbar.view.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        snackbarText.maxLines = 999

        if (actionLabel != null) {
            snackbar.setAction(actionLabel, listener ?: View.OnClickListener { snackbar.dismiss() })
        }

        snackbar.show()
    }

    fun triggerSwipeRefreshLayout(view: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener?) {
        view.post {
            view.isRefreshing = true
            listener?.onRefresh()
        }
    }

    fun startActivityWithSharedElementTrans(startActivity: AppCompatActivity, intent: Intent, vararg sharedElements: Pair<View, String>) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            startActivity.startActivity(intent)
        } else {
            val activityOptions = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(startActivity, *sharedElements)

            startActivity.startActivity(intent, activityOptions.toBundle())
        }
    }

}
