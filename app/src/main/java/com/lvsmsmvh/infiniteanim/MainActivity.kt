package com.lvsmsmvh.infiniteanim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lvsmsmvh.infinitelibrary.infiniteAnim

class MainActivity : AppCompatActivity() {

    private val anim by infiniteAnim()

    private data class AnimButton(val button: Button, val text: String, var isRunning: Boolean)
    private val buttons = mutableSetOf<AnimButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttons.add(AnimButton(findViewById(R.id.button1), "ENLARGE_SMALL", false))
        buttons.add(AnimButton(findViewById(R.id.button2), "ENLARGE_MEDIUM", false))
        buttons.add(AnimButton(findViewById(R.id.button3), "ENLARGE_BIG", false))
        buttons.add(AnimButton(findViewById(R.id.button4), "ENLARGE_BIG_AND_FAST", false))
        buttons.add(AnimButton(findViewById(R.id.button5), "MOVE_DOWN", false))

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
                        3 -> anim.enlargeBigAndFastOn(animButton.button)
                        4 -> anim.moveDownOn(animButton.button)
                    }
                }
            }
        }
    }
}