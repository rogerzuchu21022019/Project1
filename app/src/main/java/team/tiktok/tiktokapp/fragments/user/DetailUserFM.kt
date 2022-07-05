package team.tiktok.tiktokapp.fragments.user

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.detail.DetailAdapter
import team.tiktok.tiktokapp.adapter.following.FollowingVideoAdapter
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentDetailUserBinding


class DetailUserFM : Fragment(), DetailAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentDetailUserBinding
    lateinit var adapter: DetailAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater)
        clickButton()
        initRecyclerView()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_detailUserFM_to_homeFM)
            }
        }
    }

    fun initRecyclerView() {
        val mainDB = Firebase.database.getReference("videos")
        val options = FirebaseRecyclerOptions.Builder<Video>()
            .setQuery(mainDB, Video::class.java)
            .build()
        adapter = DetailAdapter(options = options)
//        binding.rvListVideo.adapter = adapter
//        binding.rvListVideo.apply {
//            setHasFixedSize(true)
//            addItemDecoration(
//                DividerItemDecoration(
//                    requireContext(),
//                    DividerItemDecoration.HORIZONTAL
//                )
//            )
//            addItemDecoration(
//                DividerItemDecoration(
//                    requireContext(),
//                    DividerItemDecoration.VERTICAL
//                )
//            )
//        }
        adapter.setOnClickItem(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding == null
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onItemClick(position: Int, view: View) {
        val id = view.id
        when (id) {
            R.id.videoView -> {
                Toast.makeText(requireContext(), "ohm $position", Toast.LENGTH_SHORT).show()
            }
        }
    }

}