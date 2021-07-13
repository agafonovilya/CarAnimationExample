package ru.agafonovilya.caranimationexample.ui.utils

import android.animation.ObjectAnimator
import android.graphics.Path
import android.view.View
import kotlin.random.Random

fun createObjectAnimatorWithPath(view: View,
                             xTo: Float, yTo: Float,
                             animationDuration: Long?): ObjectAnimator{
    val x = view.x
    val y = view.y
    val path = Path()
    path.moveTo(x, y)
    path.lineTo(xTo, yTo)
    val animation = ObjectAnimator.ofFloat(view, View.X, View.Y, path)
    if (animationDuration != null) {
        animation.duration = animationDuration
    }
    return animation
}

fun calculateRandomXCoordinates(windowWidth: Float) = Random.nextInt(windowWidth.toInt()).toFloat()
fun calculateRandomYCoordinates(windowHeight: Float) = Random.nextInt(windowHeight.toInt()).toFloat()