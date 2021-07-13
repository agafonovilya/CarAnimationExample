package ru.agafonovilya.caranimationexample.ui.utils

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue

class WindowDimensionsCalculator() {
    var windowWidth = 0F
    var windowHeight = 0F

    fun getWindowDimensions(activity: Activity): List<Float>{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowHeight = activity.windowManager.currentWindowMetrics.bounds.height().toFloat()
            windowWidth = activity.windowManager.currentWindowMetrics.bounds.width().toFloat()
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            windowHeight = displayMetrics.heightPixels.toFloat()
            windowWidth = displayMetrics.widthPixels.toFloat()
        }
        println("screen size: $windowWidth:$windowHeight")
        val tv = TypedValue()

        if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            val actionBarHeight =
                TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
            println("actionBarHeight: $actionBarHeight")
            windowHeight -= actionBarHeight
        }
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
            println("statusBarHeight: $statusBarHeight")
            windowHeight -= statusBarHeight
        }
        return listOf(windowWidth, windowHeight)
    }
}