package android.zulu13.com.wforecast.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("imageResource")
fun setImageViewResource(view: ImageView, resId : Int) {
    view.setImageResource(resId)
}


//@BindingAdapter("imageUrl")
//fun bindImage(imgView: ImageView, imgUrl: String?){
//    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//        Glide.with(imgView.context)
//            .load(imgUri)
//            .apply(RequestOptions()
//                .placeholder(R.drawable.loading_animation)
//                .error(R.drawable.ic_broken_image))
//            .into(imgView)
//
//    }
//}