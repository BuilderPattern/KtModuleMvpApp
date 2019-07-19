package kt.module.base_module.view

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

class BottomBarBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var viewY: Float = 0.toFloat()//控件距离coordinatorLayout底部距离

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, nestedScrollAxes: Int, @ViewCompat.NestedScrollType type: Int): Boolean {
        if (child.visibility == View.VISIBLE && viewY == 0f) {
            viewY = coordinatorLayout.height - child.y
        }
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0//判断是否竖直滚动
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (type == ViewCompat.TYPE_TOUCH) {
            if (dy > 20 && child.visibility == View.VISIBLE) {
                hide(child)
            } else if (dy < 20 && child.visibility != View.VISIBLE) {
                show(child)
            }
        }
    }

    //隐藏时的动画
    private fun hide(view: View) {
        AnimaUtils.hideAnim(view)
    }

    //显示时的动画
    private fun show(view: View) {
        AnimaUtils.showAnim(view)
    }
}