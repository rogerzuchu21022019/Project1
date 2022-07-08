package team.tiktok.tiktokapp.fragments.explore

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
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
        initRecyclerView(binding.rvExplore1)
        initRecyclerView(binding.rvExplore2)
        initRecyclerView(binding.rvExplore3)
        initRecyclerView(binding.rvExplore4)
        initRecyclerView(binding.rvExplore5)
        initRecyclerView(binding.rvExplore6)
        return binding.root
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {

        val mDataBase = Firebase.database.getReference("videos")
        val options = FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mDataBase, Video::class.java)
                .build()
        adapter = ExploreAdapter(options)
        recyclerView.adapter = adapter
        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL))
            addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        }
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