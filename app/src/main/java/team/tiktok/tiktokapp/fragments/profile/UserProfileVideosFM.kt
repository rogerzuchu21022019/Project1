package team.tiktok.tiktokapp.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.adapter.detail.ProfileAdapter
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentUserProfileVideosBinding


class UserProfileVideosFM : Fragment(), ProfileAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentUserProfileVideosBinding
    val auth = Firebase.auth
    lateinit var adapter: ProfileAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileVideosBinding.inflate(layoutInflater)
        initRecyclerView()
        return binding.root
    }


    fun initRecyclerView() {
        if (auth.currentUser != null) {
            val mainDB =
                Firebase.database.getReference("users").child(auth.currentUser!!.uid)
                    .child("videos")
            val options = FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mainDB, Video::class.java)
                .build()
            adapter = ProfileAdapter(options = options)
            binding.rvListVideo.adapter = adapter
            binding.rvListVideo.apply {
                setHasFixedSize(true)
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.HORIZONTAL
                    )
                )
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            adapter.setOnClickItem(this@UserProfileVideosFM)
        }


    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            adapter.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (auth.currentUser != null) {
            adapter.stopListening()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null

    }

    override fun onItemClick(position: Int, view: View) {
    }

}