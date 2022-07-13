package team.tiktok.tiktokapp.adapter.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadImage")
fun loadImage(iv: ImageView, imgUrl: String?) {
    Glide.with(iv).load(imgUrl).into(iv)
}

fun loadList() {

}
