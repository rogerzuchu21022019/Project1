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
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
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
        when (id) {
            R.id.ivFavorite -> {

            }
            R.id.ivSearch -> {
                findNavController().navigate(R.id.action_followingFM_to_searchFM)
            }
            R.id.civUser -> {
                transferData()
            }
            R.id.tvForU -> {
                findNavController().navigate(R.id.action_followingFM_to_homeFM)

            }
            R.id.ivComment -> {
                isLogIn(position)
            }
            R.id.ivSave -> {
                Toast.makeText(requireContext(), "save", Toast.LENGTH_SHORT).show()
            }
            R.id.ivShare -> {
                findNavController().navigate(R.id.action_followingFM_to_shareBottomSheetFM)

            }
        }

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
                                navDicretion(user)
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
    fun navDicretion(user: User) {
        if(activity?.findNavController(R.id.fmNavHostGraph)!!.currentDestination!!.id == R.id.followingFM ){
            val action = FollowingFMDirections.actionFollowingFMToDetailUserFM(user)
            activity?.findNavController(R.id.fmNavHostGraph)!!.lifeCycleNavigate(lifecycleScope,action)
//            findNavController().navigate(action)
//            findNavController().popBackStack(R.id.detailUserFM,false,true)
        }
    }
    fun NavController.lifeCycleNavigate(lifecycle : LifecycleCoroutineScope, navDirections: NavDirections) =
        lifecycle.launchWhenResumed {
            navigate(navDirections)
        }

    private fun isLogIn(position: Int) {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            loadComments(position = position)
        } else {
            val action = FollowingFMDirections.actionFollowingFMToSignUpBottomSheetFM()
            findNavController().navigate(action)
        }
    }

    fun loadComments(position: Int) {
        val video = adapter.getItem(position)
        val action = FollowingFMDirections.actionFollowingFMToCommentBottomSheetFM(video)
        findNavController().navigate(action)
    }
}