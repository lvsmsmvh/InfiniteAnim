package com.lvsmsmvh.infinitelibrary

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner


fun LifecycleOwner.infiniteAnim() = lazy { InfiniteAnim(lifecycle) }

class InfiniteAnim(private val lifecycle: Lifecycle) {

    private enum class AnimType(val animRes: Int) {
        ENLARGE_SMALL(R.anim.animation_enlarge_small),
        ENLARGE_MEDIUM(R.anim.animation_enlarge_medium),
        ENLARGE_BIG(R.anim.animation_enlarge_big),
        ENLARGE_BIG_AND_FAST(R.anim.animation_enlarge_big_and_fast),
        MOVE_DOWN(R.anim.animation_move_up_down);
    }

    private val mapIsViewEnabled = mutableMapOf<View, Boolean>()

    fun enlargeBigAndFastOn(view: View, startOffset: Long = 0L, repeatDelay: Long = 0L) {
        animOn(view, AnimType.ENLARGE_BIG_AND_FAST, startOffset, repeatDelay)
    }

    fun enlargeSmallOn(view: View, startOffset: Long = 0L, repeatDelay: Long = 0L) {
        animOn(view, AnimType.ENLARGE_SMALL, startOffset, repeatDelay)
    }

    fun enlargeMediumOn(view: View, startOffset: Long = 0L, repeatDelay: Long = 0L) {
        animOn(view, AnimType.ENLARGE_MEDIUM, startOffset, repeatDelay)
    }

    fun enlargeBigOn(view: View, startOffset: Long = 0L, repeatDelay: Long = 0L) {
        animOn(view, AnimType.ENLARGE_BIG, startOffset, repeatDelay)
    }

    fun moveDownOn(view: View, startOffset: Long = 0L, repeatDelay: Long = 0L) {
        animOn(view, AnimType.MOVE_DOWN, startOffset, repeatDelay)
    }

    fun cancelOn(view: View) {
        mapIsViewEnabled[view] = false
        view.clearAnimation()
        view.animation?.cancel()
    }

    private fun animOn(view: View, animType: AnimType, startOffset: Long, delay: Long) {
        cancelOn(view)
        addToMap(view)
        val anim = AnimationUtils.loadAnimation(view.context, animType.animRes)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                Thread {
                    Thread.sleep(delay)
                    if (!isViewEnabled(view)) return@Thread
                    if (lifecycle.currentState == Lifecycle.State.DESTROYED) return@Thread
                    Handler(Looper.getMainLooper()).post {
                        view.startAnimation(anim)
                    }
                }.start()
            }
        })


        Handler(Looper.getMainLooper()).postDelayed({
            if (lifecycle.currentState == Lifecycle.State.DESTROYED) return@postDelayed
            view.startAnimation(anim)
        }, startOffset)
    }

    private fun addToMap(view: View) {
        mapIsViewEnabled[view] = true
    }

    private fun isViewEnabled(view: View): Boolean {
        return mapIsViewEnabled[view] ?: false
    }
}