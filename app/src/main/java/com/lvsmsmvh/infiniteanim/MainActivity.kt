package com.lvsmsmvh.infiniteanim

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.lvsmsmvh.infinitelibrary.infiniteAnim

class MainActivity : AppCompatActivity() {

    private val anim by infiniteAnim()

    private data class AnimButton(val button: Button, val text: String, var isRunning: Boolean)
    private val buttons = mutableListOf<AnimButton>()

    private var isCircleVisible = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttons.add(AnimButton(findViewById(R.id.button1), "ENLARGE_SMALL", false))
        buttons.add(AnimButton(findViewById(R.id.button2), "ENLARGE_MEDIUM", false))
        buttons.add(AnimButton(findViewById(R.id.button3), "ENLARGE_BIG", false))
        buttons.add(AnimButton(findViewById(R.id.button4), "DISAPPEAR_SLOW_ONCE", false))
        buttons.add(AnimButton(findViewById(R.id.button5), "DISAPPEAR", false))

        buttons.forEachIndexed { index, animButton ->
            animButton.button.text = animButton.text

            animButton.button.setOnClickListener {
                if (animButton.isRunning) {
                    // Stop anim
                    animButton.isRunning = false
                    anim.cancelOn(animButton.button)
                } else {
                    // Start anim
                    animButton.isRunning = true
                    when (index) {
                        0 -> anim.enlargeSmallOn(animButton.button)
                        1 -> anim.enlargeMediumOn(animButton.button)
                        2 -> anim.enlargeBigOn(animButton.button)
                        3 -> anim.disappearSlowOn(animButton.button, doOnlyOnce = true)
                    }
                }
            }
        }

        val circle = findViewById<ImageView>(R.id.circle)
        val button4 = buttons[4].button
        button4.setOnClickListener {
            isCircleVisible = when (isCircleVisible) {
                true -> {
                    anim.disappearSlowOn(circle, doOnlyOnce = true)
                    button4.text = "APPEAR"
                    false
                }
                false -> {
                    anim.appearSlowOn(circle, doOnlyOnce = true)
                    button4.text = "DISAPPEAR"
                    true
                }
            }

            val interp0 = circle.animation?.interpolator?.getInterpolation(0.0f)
            val interp05 = circle.animation?.interpolator?.getInterpolation(0.5f)
            val interp1 = circle.animation?.interpolator?.getInterpolation(1.0f)

            Log.i("tag_alpha", "Current circle alpha: " + circle.alpha)
            Log.i("tag_alpha", "Current circle interp 0.0: " + interp0)
            Log.i("tag_alpha", "Current circle interp 0.5: " + interp05)
            Log.i("tag_alpha", "Current circle interp 1.0: " + interp1)

        }
    }
}