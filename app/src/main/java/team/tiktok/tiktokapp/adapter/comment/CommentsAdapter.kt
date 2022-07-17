package team.tiktok.tiktokapp.adapter.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import team.tiktok.tiktokapp.data.Comment
import team.tiktok.tiktokapp.databinding.ItemRvCommentBinding

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    var listComment = mutableListOf<Comment>()
    lateinit var binding: ItemRvCommentBinding
    class CommentsViewHolder(val binding: ItemRvCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment){
            binding.comment = comment
            binding.user = comment.users
            binding.video = comment.videos
            binding.executePendingBindings()
        }
    }
    fun setAdapter(listComment:MutableList<Comment>){
        this.listComment = listComment
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        binding = ItemRvCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = listComment[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return listComment.size
    }
}