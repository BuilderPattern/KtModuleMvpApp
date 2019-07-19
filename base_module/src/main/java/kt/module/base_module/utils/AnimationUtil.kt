package kt.module.base_module.utils

import android.animation.Animator
import android.view.View
import android.view.animation.LinearInterpolator

object AnimationUtil {

    private val INTERPOLATOR = LinearInterpolator()

    fun hideAnim(view: View) {

        val animator = view.animate().translationY(view.height.toFloat())
            .setInterpolator(INTERPOLATOR)
            .setDuration(300)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.GONE
            }

            override fun onAnimationCancel(animator: Animator) {
                showAnim(view)
            }

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animator.start()
    }

    fun showAnim(view: View) {

        val animator = view.animate().translationY(0f)
            .setInterpolator(INTERPOLATOR)
            .setDuration(300)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animator: Animator) {}

            override fun onAnimationCancel(animator: Animator) {
                hideAnim(view)
            }

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animator.start()
    }
}