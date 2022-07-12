package team.tiktok.tiktokapp.adapter.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import team.tiktok.tiktokapp.R

@BindingAdapter("app:loadImage")
fun loadImage(iv: ImageView, imgUrl: String) {
    Glide.with(iv).load(imgUrl).placeholder(R.drawable.girl).error(R.drawable.girl).into(iv)
}
  
