package team.tiktok.tiktokapp.fragments.user

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.detail.DetailAdapter
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
        initRecyclerView()
        getData()
        return binding.root
    }

    fun getData(){
         val getDataFromDetailUserFM = requireParentFragment().navArgs<DetailUserFMArgs>().value
        val user = getDataFromDetailUserFM.user
        Toast.makeText(requireContext(),"fullname = ${user.fullName}",Toast.LENGTH_SHORT).show()
        Log.d("UserDetailVideosFM","$user")
    }


    fun initRecyclerView() {
            val mainDB = Firebase.database.getReference("users")
                val options = FirebaseRecyclerOptions.Builder<Video>()
                    .setQuery(mainDB, Video::class.java)
                    .build()

                adapter = DetailAdapter(options = options)
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