package team.tiktok.tiktokapp.adapter.bindingadapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

@androidx.databinding.BindingAdapter("app:loadImage")
fun loadImage(iv: ImageView, imgUrl: String?=null) {
    Glide.with(iv).load(imgUrl!!).into(iv)
}

fun loadList() {

}
