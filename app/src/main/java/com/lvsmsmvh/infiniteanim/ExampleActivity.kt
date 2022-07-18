package com.lvsmsmvh.infiniteanim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lvsmsmvh.infinitelibrary.infiniteAnim

class ExampleActivity : AppCompatActivity() {

    private val anim by infiniteAnim()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        val button = findViewById<Button>(R.id.button1)
        anim.enlargeSmallOn(button, startOffset = 1_000L, repeatDelay = 500L)
    }
}