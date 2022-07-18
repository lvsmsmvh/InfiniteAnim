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
        MOVE_DOWN(R.anim.animation_move_up_down),
        APPEAR_ALPHA_SLOW(R.anim.animation_alpha_appear_slow),
        APPEAR_ALPHA_NORMAL(R.anim.animation_alpha_appear_normal),
        APPEAR_ALPHA_FAST(R.anim.animation_alpha_appear_fast),
    }

    private val mapIsViewEnabled = mutableMapOf<View, Boolean>()

    fun appearSlowOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.APPEAR_ALPHA_SLOW, startOffset, repeatDelay, doOnlyOnce)
    }

    fun appearOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.APPEAR_ALPHA_NORMAL, startOffset, repeatDelay, doOnlyOnce)
    }

    fun appearFastOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.APPEAR_ALPHA_FAST, startOffset, repeatDelay, doOnlyOnce)
    }

    fun enlargeBigAndFastOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.ENLARGE_BIG_AND_FAST, startOffset, repeatDelay, doOnlyOnce)
    }

    fun enlargeSmallOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.ENLARGE_SMALL, startOffset, repeatDelay, doOnlyOnce)
    }

    fun enlargeMediumOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.ENLARGE_MEDIUM, startOffset, repeatDelay, doOnlyOnce)
    }

    fun enlargeBigOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.ENLARGE_BIG, startOffset, repeatDelay, doOnlyOnce)
    }

    fun moveDownOn(
        view: View, startOffset: Long = 0L, repeatDelay: Long = 0L,
        doOnlyOnce: Boolean = false,
    ) {
        animOn(view, AnimType.MOVE_DOWN, startOffset, repeatDelay, doOnlyOnce)
    }

    fun cancelOn(view: View) {
        mapIsViewEnabled[view] = false
        view.clearAnimation()
        view.animation?.cancel()
    }

    private fun animOn(
        view: View,
        animType: AnimType,
        startOffset: Long,
        delay: Long,
        doOnlyOnce: Boolean
    ) {
        cancelOn(view)
        addToMap(view)
        val anim = AnimationUtils.loadAnimation(view.context, animType.animRes)

        if (!doOnlyOnce) {
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    Thread {
                        Thread.sleep(delay)
                        Handler(Looper.getMainLooper()).post {
                            tryToPlayAnim(view, anim)
                        }
                    }.start()
                }
            })
        }


        Handler(Looper.getMainLooper()).postDelayed({
            tryToPlayAnim(view, anim)
        }, startOffset)
    }

    private fun tryToPlayAnim(view: View, anim: Animation) {
        if (!isViewEnabled(view)) return
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) return
        view.startAnimation(anim)

    }

    private fun addToMap(view: View) {
        mapIsViewEnabled[view] = true
    }

    private fun isViewEnabled(view: View): Boolean {
        return mapIsViewEnabled[view] ?: false
    }
}