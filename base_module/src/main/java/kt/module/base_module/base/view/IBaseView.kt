package kt.module.common_module.base.view

import io.reactivex.ObservableTransformer
import com.trello.rxlifecycle2.android.ActivityEvent

interface IBaseView {

    fun <T> bindToLifecycle(): ObservableTransformer<T, T>

    fun <T> bindUntilEvent(event: ActivityEvent): ObservableTransformer<T, T>
}