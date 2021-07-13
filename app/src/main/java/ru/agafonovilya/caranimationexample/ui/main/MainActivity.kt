package ru.agafonovilya.caranimationexample.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View.ROTATION
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import ru.agafonovilya.caranimationexample.databinding.ActivityMainBinding
import ru.agafonovilya.caranimationexample.ui.utils.WindowDimensionsCalculator
import ru.agafonovilya.caranimationexample.ui.utils.calculateRandomXCoordinates
import ru.agafonovilya.caranimationexample.ui.utils.calculateRandomYCoordinates
import ru.agafonovilya.caranimationexample.ui.utils.createObjectAnimatorWithPath

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private var windowWidth = 0F
    private var windowHeight = 0F

    private var lastRotation = 0f
    private var nextRotation = 15f

    private val set = AnimatorSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindowDimensions()
        binding.carView.setOnClickListener { clickListener() }
    }

    private fun clickListener() {
        if (set.isStarted) {
            set.removeAllListeners()
            set.end()
        } else {
            configureSetToRandomCoordinates()
            set.doOnEnd {
                configureSetToRandomCoordinates()
                set.start()
            }
            set.start()
        }
    }

    private fun configureSetToRandomCoordinates() {

        val animationRotation = ObjectAnimator.ofFloat(binding.carView, ROTATION, lastRotation, nextRotation)
        animationRotation.interpolator = LinearInterpolator()
        animationRotation.duration = 300
        lastRotation += 15f
        nextRotation += 15f

        val newX = calculateRandomXCoordinates(windowWidth)
        val newY = calculateRandomYCoordinates(windowHeight)
        val animationTranslation = createObjectAnimatorWithPath(binding.carView,newX, newY,1000 )
        animationTranslation.interpolator = AccelerateDecelerateInterpolator()

        set.playSequentially(animationRotation, animationTranslation)
    }

    private fun getWindowDimensions() {
        val windowDimensions = WindowDimensionsCalculator().getWindowDimensions(this)
        windowWidth = windowDimensions[0]
        windowHeight = windowDimensions[1]
    }

    override fun onPause() {
        if (set.isStarted) {
            set.removeAllListeners()
            set.end()
        }
        super.onPause()
    }
}
