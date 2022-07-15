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
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
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
//        loadUser()
        initRecyclerView()
        return binding.root
    }

    fun loadUser() {
        CoroutineScope(SupervisorJob()).launch(Dispatchers.IO){
            val auth = Firebase.auth
            val database = Firebase.database.getReference("users")
            var isCheck: Boolean? = null

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (element in snapshot.children) {
                        var uuid = element.child("uuid").getValue(String::class.java)
                        if (auth.currentUser!!.uid == uuid) {
                            database.child(element.key!!)
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val user = snapshot.getValue<User>()!!
                                        Log.d("ProfileFM", "user : $user")
                                        isCheck = true
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }
                                })
                        }
                        if (isCheck == true) {
                            break
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
            database.addValueEventListener(listener)
        }
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