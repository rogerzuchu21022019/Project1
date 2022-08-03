package team.tiktok.tiktokapp.fragments.user

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.adapter.detail.DetailAdapter
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentUserDetailVideosBinding


class UserDetailVideosFM : Fragment(), DetailAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentUserDetailVideosBinding
    lateinit var adapter: DetailAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailVideosBinding.inflate(layoutInflater)
//        getData()
        initRecyclerView()
        return binding.root
    }

    fun getData(): User {
        val getDataFromDetailUserFM = requireParentFragment().navArgs<DetailUserFMArgs>().value
        val user = getDataFromDetailUserFM.user
        return user
    }


    fun initRecyclerView() {
            val mainDB =
                Firebase.database.getReference("users").child(getData().uuid!!).child("videos")
            val options = FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mainDB, Video::class.java)
                .build()
            adapter = DetailAdapter(options = options)
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
                    adapter.setOnClickItem(this@UserDetailVideosFM)
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