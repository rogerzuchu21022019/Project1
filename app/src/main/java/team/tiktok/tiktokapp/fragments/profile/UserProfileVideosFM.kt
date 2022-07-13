package team.tiktok.tiktokapp.fragments.profile

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.detail.ProfileAdapter
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
            val mainDB = Firebase.database.getReference("users")
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
                adapter.setOnClickItem(this)
    }

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        } else {
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
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