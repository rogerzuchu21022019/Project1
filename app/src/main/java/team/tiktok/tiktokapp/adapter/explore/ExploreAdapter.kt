package team.tiktok.tiktokapp.adapter.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.ItemExploreBinding

//class VideoAdapter(options: FirebaseRecyclerOptions<Video?>,private val clickItem:ClickItemListener):FirebaseRecyclerAdapter<Video, VideoAdapter.VideoViewHolder>(options) {
class ExploreAdapter(options: FirebaseRecyclerOptions<Video?>):FirebaseRecyclerAdapter<Video, ExploreAdapter.VideoViewHolder>(options) {
    lateinit var itemVideoBinding: ItemExploreBinding
    lateinit var onClickItemInRecyclerView: OnClickItemInRecyclerView
    class VideoViewHolder(val itemVideoBinding: ItemExploreBinding
    , onClickItemInRecyclerView: OnClickItemInRecyclerView
    ) :RecyclerView.ViewHolder(itemVideoBinding.root){
        var isFav = false
        var isSave = false
        var isShare = false

        ///init Click
        init {


            /// Click on Screen
            itemVideoBinding.root.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }

            itemVideoBinding.videoView.apply {

                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)

                }
            }
            onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,itemVideoBinding.root)

        }
        fun setData(video:Video,position: Int){
            itemVideoBinding.video = video
            itemVideoBinding.videoView.apply {
                setVideoPath(video.url)
                setOnPreparedListener { mediaplayer->
                    mediaplayer.start()
                    mediaplayer.setVolume(0f,0f)
                }
            }
        }
    }

    fun setOnClickItem(onClickItemInRecyclerView: OnClickItemInRecyclerView) {
        this.onClickItemInRecyclerView = onClickItemInRecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        itemVideoBinding =  ItemExploreBinding.inflate(layoutInflater,parent,false)
        return VideoViewHolder(itemVideoBinding = itemVideoBinding,onClickItemInRecyclerView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, video: Video) {
        holder.setData(video,position)
    }
    open interface OnClickItemInRecyclerView {
        fun onItemClick(position: Int,view:View)
    }

}
//class ClickItemListener(private var clickItem:(video: Video)->Unit){
//    fun  onClick(video: Video) = clickItem(video)
//}