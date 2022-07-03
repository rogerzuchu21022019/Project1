package team.tiktok.tiktokapp.adapter

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.R.color.white
import team.tiktok.tiktokapp.activities.MainActivity
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.ItemVideoBinding

//class VideoAdapter(options: FirebaseRecyclerOptions<Video?>,private val clickItem:ClickItemListener):FirebaseRecyclerAdapter<Video, VideoAdapter.VideoViewHolder>(options) {
class VideoAdapter(options: FirebaseRecyclerOptions<Video?>):FirebaseRecyclerAdapter<Video, VideoAdapter.VideoViewHolder>(options) {
    lateinit var itemVideoBinding: ItemVideoBinding
    lateinit var onClickItemInRecyclerView: OnClickItemInRecyclerView
    class VideoViewHolder(val itemVideoBinding: ItemVideoBinding
    , onClickItemInRecyclerView: OnClickItemInRecyclerView) :RecyclerView.ViewHolder(itemVideoBinding.root){
        var isFav = false
        var isSave = false
        private var isShare = false

        ///init Click
        init {
            itemVideoBinding.tvForU.setTextColor(ContextCompat.getColor(this.itemVideoBinding.root.context,R.color.white))

            /// Click Icon Search
            itemVideoBinding.ivSearch.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }

            /// Click on Screen
            itemVideoBinding.root.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }
            itemVideoBinding.civUser.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }

            /// Click Following | For you
            itemVideoBinding.tvForU.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }
            itemVideoBinding.tvFollowing.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }

            /// Click Icon Favorite
            itemVideoBinding.ivFavorite.apply {
                setOnClickListener {
                    if (!isFav){
                        itemVideoBinding.ivFavorite.setImageResource(R.drawable.heart)
                        itemVideoBinding.ivFavorite.setColorFilter(it.resources.getColor(R.color.white))
                        isFav = true
                    }else{
                        itemVideoBinding.ivFavorite.setImageResource(R.drawable.fill_heart)
                        isFav = false
                    }
                }
            }

            /// Click Icon Comment
            itemVideoBinding.ivComment.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }

            /// Click Icon Save Clip
            itemVideoBinding.ivSave.apply {
                setOnClickListener {
                    if (isSave){
                        itemVideoBinding.ivSave.setImageResource(R.drawable.fill_favorite)
                        isSave = true
                    }else{
                        isSave = false
                    }
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }

            /// Click Icon Share
            itemVideoBinding.ivShare.apply {
                setOnClickListener {
                    if (isShare){
                        itemVideoBinding.ivShare.setImageResource(R.drawable.share)
                        itemVideoBinding.ivShare.setColorFilter(it.resources.getColor(R.color.white))
                        isShare = true
                    }else{
                        itemVideoBinding.ivShare.setImageResource(R.drawable.share)
                        itemVideoBinding.ivShare.setColorFilter(it.resources.getColor(R.color.white))

                        isShare = false
                    }
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }
            onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,itemVideoBinding.root)
        }
        fun setData(video:Video){
            val mediaController =  MediaController(this.itemVideoBinding.root.context)
            itemVideoBinding.video = video
            mediaController
            itemVideoBinding.videoView.apply {
                setVideoPath(video.url)
                setOnPreparedListener { mediaplayer->
                    mediaplayer.start()
                    mediaplayer.isLooping = true

                    itemVideoBinding.root.apply {
                        setOnClickListener {
                            if(mediaplayer.isPlaying ){
                                mediaplayer.pause()
                                itemVideoBinding.ivPlay.visibility = View.VISIBLE
                            }else{
                                mediaplayer.start()
                                itemVideoBinding.ivPlay.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    fun setOnClickItem(onClickItemInRecyclerView: OnClickItemInRecyclerView) {
        this.onClickItemInRecyclerView = onClickItemInRecyclerView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        itemVideoBinding = ItemVideoBinding.inflate(layoutInflater,parent,false)

        return VideoViewHolder(itemVideoBinding = itemVideoBinding,onClickItemInRecyclerView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, video: Video) {
        holder.setData(video)
    }
    open interface OnClickItemInRecyclerView {
        fun onItemClick(position: Int,view:View)
    }

}
//class ClickItemListener(private var clickItem:(video: Video)->Unit){
//    fun  onClick(video: Video) = clickItem(video)
//}