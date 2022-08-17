package team.tiktok.tiktokapp.adapter.home

import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.coroutines.*
import pl.droidsonroids.gif.GifImageButton
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.ItemVideoHomeBinding

class HomeVideoAdapter(options: FirebaseRecyclerOptions<Video?>) :
    FirebaseRecyclerAdapter<Video, HomeVideoAdapter.VideoViewHolder>(options) {
    lateinit var itemVideoBinding: ItemVideoHomeBinding
    lateinit var onClickItemInRecyclerView: OnClickItemInRecyclerView


    @RequiresApi(Build.VERSION_CODES.P)
    class VideoViewHolder(
        val itemVideoBinding: ItemVideoHomeBinding,
        onClickItemInRecyclerView: OnClickItemInRecyclerView
    ) : RecyclerView.ViewHolder(itemVideoBinding.root) {
        var isFav = false
        var isSave = false
        private var isShare = false

        ///init Click
        init {
            itemVideoBinding.tvForU.setTextColor(
                ContextCompat.getColor(
                    this.itemVideoBinding.root.context,
                    R.color.white
                )
            )

            /// Click Icon Search
            itemVideoBinding.ivSearch.apply {
                setOnClickListener {
                }
            }

            /// Click on Screen
            itemVideoBinding.root.apply {
                setOnClickListener {
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
                }
            }
            itemVideoBinding.tvFollowing.apply {
                setOnClickListener {
                }
            }

            /// Click Icon Favorite
            itemVideoBinding.ivFavorite.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition, it)
                    if (isFav) {
                        isFav = false
                        itemVideoBinding.ivFavorite.setImageResource(R.drawable.heart_white)

                    } else {
                        isFav = true
                        itemVideoBinding.ivFavorite.setImageResource(R.drawable.heart_red)
                    }
                }
            }
            itemVideoBinding.ivSave.apply {
                setOnClickListener {

                }
            }

            itemVideoBinding.ivShare.apply {
                setOnClickListener {

                }
            }
            /// Click Icon Comment
            itemVideoBinding.ivComment.apply {
                setOnClickListener {
                    onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition,it)
                }
            }


            val btnGif = GifImageButton(this.itemVideoBinding.root.context)
            btnGif.setImageResource(R.drawable.disc)
            onClickItemInRecyclerView.onItemClick(absoluteAdapterPosition, itemVideoBinding.root)
        }

        fun setData(video: Video) {
            CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {

                val source = ImageDecoder.createSource(
                    this@VideoViewHolder.itemView.resources, R.drawable.disc
                )
                val drawable = ImageDecoder.decodeDrawable(source)

                itemVideoBinding.gif.post {
                    itemVideoBinding.gif.setImageDrawable(drawable)
                    (drawable as? AnimatedImageDrawable)?.start()
                }
                itemVideoBinding.video = video
                itemVideoBinding.user = video.user
                itemVideoBinding.comment = video.comments

                itemVideoBinding.videoView.apply {
                    withContext(Dispatchers.Main) {
                        setVideoPath(video.url)
                        setOnPreparedListener { mediaplayer ->
                            mediaplayer.start()
                            mediaplayer.isLooping = true
                            itemVideoBinding.root.apply {
                                setOnClickListener {
                                    if (mediaplayer.isPlaying) {
                                        mediaplayer.pause()
                                        if ((drawable as? AnimatedImageDrawable)?.isRunning() == true) {
                                            itemVideoBinding.gif.setImageDrawable(drawable)
                                            (drawable as? AnimatedImageDrawable)?.stop()
                                        }
                                        itemVideoBinding.ivPlay.visibility = View.VISIBLE

                                    } else {
                                        mediaplayer.start()
                                        if ((drawable as? AnimatedImageDrawable)?.isRunning() == false) {
                                            itemVideoBinding.gif.setImageDrawable(drawable)
                                            (drawable as? AnimatedImageDrawable)?.start()
                                        }
                                        itemVideoBinding.ivPlay.visibility = View.GONE
                                    }
                                }
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


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        itemVideoBinding = ItemVideoHomeBinding.inflate(layoutInflater, parent, false)

        return VideoViewHolder(
            itemVideoBinding = itemVideoBinding,
            onClickItemInRecyclerView = onClickItemInRecyclerView
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, video: Video) {
        holder.setData(video)
    }

    interface OnClickItemInRecyclerView {
        fun onItemClick(position: Int, view: View)
    }

}