package team.tiktok.tiktokapp.fragments.following

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.following.FollowingVideoAdapter
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentHomeBinding
import team.tiktok.tiktokapp.fragments.home.HomeFMDirections


class FollowingFM : Fragment(), FollowingVideoAdapter.OnClickItemInRecyclerView {
   lateinit var binding:FragmentHomeBinding
    private lateinit var adapter : FollowingVideoAdapter
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        loadData()
        auth = Firebase.auth
        checkComeIn(true)
        return binding.root
    }



    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val mDataBase = Firebase.database.getReference("videos")
            val options = FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mDataBase,Video::class.java)
                .build()
            withContext(Dispatchers.Main){
                adapter = FollowingVideoAdapter(options)
                binding.vpHome.adapter = adapter
                adapter.setOnClickItem(this@FollowingFM)
            }
        }


    }

    override fun onStart() {
        super.onStart()
        var handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(Runnable {
            binding.wait.visibility = View.GONE
            adapter.startListening()
        },500)
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }



    override fun onItemClick(position: Int, view: View) {
        val id = view.id
        if (id==R.id.ivSearch){
            findNavController().navigate(R.id.action_followingFM_to_searchFM)
        }
        if (id==R.id.civUser){

            findNavController().navigate(R.id.action_followingFM_to_detailUserFM)
        }
        if (id==R.id.tvForU){
            findNavController().navigate(R.id.action_followingFM_to_homeFM)

        }
        if (id==R.id.ivComment){
            isLogIn()
            Toast.makeText(requireContext(),"comment",Toast.LENGTH_SHORT).show()
        }
        if (id==R.id.ivSave){
            Toast.makeText(requireContext(),"save",Toast.LENGTH_SHORT).show()
        }
        if (id==R.id.ivShare){
            Toast.makeText(requireContext(),"share",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_followingFM_to_shareBottomSheetFM)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        checkComeIn(false)
        binding == null
    }
    private fun checkComeIn(isComeIn:Boolean){
        if (isComeIn){
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.setBackgroundResource(R.color.black)
            navBot.tabColorSelected = ContextCompat.getColor(requireContext(),R.color.white)

        }
    }
    fun navSignUp() {
        val action = FollowingFMDirections.actionFollowingFMToSignUpBottomSheetFM()
        findNavController().navigate(action)
    }

    private fun isLogIn() {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            val action = FollowingFMDirections.actionFollowingFMToCommentBottomSheetFM()
            findNavController().navigate(action)
//            checkExist(auth.currentUser!!.uid)

        } else {
            navSignUp()

        }
    }

}