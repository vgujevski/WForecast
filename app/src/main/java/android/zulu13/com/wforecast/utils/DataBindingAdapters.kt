package android.zulu13.com.wforecast.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("imageResource")
fun setImageViewResource(view: ImageView, resId : Int) {
    view.setImageResource(resId)
}
