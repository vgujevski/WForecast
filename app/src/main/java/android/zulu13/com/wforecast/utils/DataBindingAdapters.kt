package android.zulu13.com.wforecast.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("imageResource")
fun setImageViewResource(view: ImageView, resId: Int) {
    view.setImageResource(resId)
}

@BindingAdapter("layoutVisible")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}