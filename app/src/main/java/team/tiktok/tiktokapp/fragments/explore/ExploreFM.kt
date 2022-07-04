package team.tiktok.tiktokapp.fragments.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.explore.ExploreAdapter
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentExploreBinding


class ExploreFM : Fragment(), ExploreAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentExploreBinding
    lateinit var adapter: ExploreAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        val mDataBase = Firebase.database.getReference("videos")
        val options = FirebaseRecyclerOptions.Builder<Video>()
            .setQuery(mDataBase, Video::class.java)
            .build()
        adapter = ExploreAdapter(options)
        binding.rvExplore.adapter = adapter
        adapter.setOnClickItem(this)
    }

    override fun onItemClick(position: Int, view: View) {
        val id = view.id
        when (id) {
            R.id.videoView -> {
                Toast.makeText(requireContext(), "ohm $position", Toast.LENGTH_SHORT).show()
            }
        }
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
}