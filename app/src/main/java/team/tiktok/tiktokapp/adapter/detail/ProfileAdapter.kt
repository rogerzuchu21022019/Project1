package team.tiktok.tiktokapp.adapter.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.ItemProfileVideoUserBinding

class ProfileAdapter(options: FirebaseRecyclerOptions<Video>) :
    FirebaseRecyclerAdapter<Video, ProfileAdapter.VideoViewHolder>(options) {
    lateinit var itemDetailVideoUserBinding: ItemProfileVideoUserBinding
    lateinit var onClickItemInRecyclerView: OnClickItemInRecyclerView

    class VideoViewHolder(
        val itemDetailVideoUserBinding: ItemProfileVideoUserBinding,
        onClickItemInRecyclerView: OnClickItemInRecyclerView
    ) : RecyclerView.ViewHolder(itemDetailVideoUserBinding.root) {
        // TODO:init Click
        init {
            //  TODO: Click on Screen
            itemDetailVideoUserBinding.root.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition, it)
                }
            }
            itemDetailVideoUserBinding.videoView.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition, it)
                }
            }
            onClickItemInRecyclerView.onItemClick(
                absoluteAdapterPosition,
                itemDetailVideoUserBinding.root
            )
        }

        fun setData(video: Video) {
            itemDetailVideoUserBinding.video = video
            itemDetailVideoUserBinding.videoView.apply {
                setVideoPath(video.url)
                setOnPreparedListener { mediaplayer ->
                    mediaplayer.start()
                    mediaplayer.setVolume(0f, 0f)
                    mediaplayer.pause()
                }
            }
        }
    }

    fun setOnClickItem(onClickItemInRecyclerView: OnClickItemInRecyclerView) {
        this.onClickItemInRecyclerView = onClickItemInRecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        itemDetailVideoUserBinding =
            ItemProfileVideoUserBinding.inflate(layoutInflater,parent,false)
        return VideoViewHolder(
            itemDetailVideoUserBinding = itemDetailVideoUserBinding,
            onClickItemInRecyclerView
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, video: Video) {
        holder.setData(video)
    }

    open interface OnClickItemInRecyclerView {
        fun onItemClick(position: Int, view: View)
    }

}
//class ClickItemListener(private var clickItem:(video: Video)->Unit){
//    fun  onClick(video: Video) = clickItem(video)
//}