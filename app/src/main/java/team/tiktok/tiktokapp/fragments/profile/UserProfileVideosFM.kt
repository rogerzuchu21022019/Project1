package team.tiktok.tiktokapp.fragments.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import team.tiktok.tiktokapp.adapter.detail.ProfileAdapter
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentUserProfileVideosBinding


class UserProfileVideosFM : Fragment(), ProfileAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentUserProfileVideosBinding
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
        val mainDB = Firebase.database.getReference("users").child("namios").child("videos")
        val options = FirebaseRecyclerOptions.Builder<Video>()
            .setQuery(mainDB, Video::class.java)
            .build()
        adapter = ProfileAdapter(options = options)
        val handle = Handler(Looper.myLooper()!!)
        handle.postDelayed({
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
        }, 500)

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null

    }

    override fun onItemClick(position: Int, view: View) {
    }

}