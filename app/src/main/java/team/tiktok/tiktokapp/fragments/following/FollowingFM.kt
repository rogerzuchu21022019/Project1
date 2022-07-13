package team.tiktok.tiktokapp.fragments.following

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.home.FollowingVideoAdapter
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentFollowingBinding


class FollowingFM : Fragment(), FollowingVideoAdapter.OnClickItemInRecyclerView {
   lateinit var binding:FragmentFollowingBinding
    private lateinit var adapter : FollowingVideoAdapter
    lateinit var auth: FirebaseAuth
    lateinit var mDataBase : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        loadData()
        auth = Firebase.auth
        checkComeIn(true)
        return binding.root
    }
    private fun transferData(){

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    snapshot.children.forEach {
                        val video = it.getValue(Video::class.java)
                        if (video == null){
                            return
                        }else{
                            val user = it.child("user").getValue(User::class.java)
                            if (user==null){
                                Log.d("UserLog","$")
                                return
                            }else{
                                navDicretion(user=user)
                                Log.d("UserLog","${user}")
                            }
                        }
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        mDataBase.addValueEventListener(listener)

    }




    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            mDataBase = Firebase.database.getReference("videos")
            val options = FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mDataBase,Video::class.java)
                .build()
            withContext(Dispatchers.Main){
                adapter = FollowingVideoAdapter(options)
                binding.vpFollowing.adapter = adapter
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
            transferData()
        }
        if (id==R.id.tvForU){
            findNavController().navigate(R.id.action_followingFM_to_homeFM)

        }
        if (id==R.id.ivComment){
            isLogIn()
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
    fun navDicretion(user:User) {
        if (findNavController().currentDestination?.id == R.id.followingFM) {
            val action = FollowingFMDirections.actionFollowingFMToDetailUserFM(user)
            findNavController().navigate(action)
        }
    }

    private fun isLogIn() {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            val action = FollowingFMDirections.actionFollowingFMToCommentBottomSheetFM()
            findNavController().navigate(action)
        } else {
            val action = FollowingFMDirections.actionFollowingFMToSignUpBottomSheetFM()
            findNavController().navigate(action)

        }
    }

}