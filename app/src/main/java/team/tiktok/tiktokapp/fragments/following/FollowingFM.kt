package team.tiktok.tiktokapp.fragments.following

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import team.tiktok.tiktokapp.data.Favorite
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentFollowingBinding


class FollowingFM : Fragment(), FollowingVideoAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowingVideoAdapter
    lateinit var auth: FirebaseAuth
    lateinit var mDataBase: DatabaseReference
    val listFavorite = mutableListOf<Favorite>()

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
                .setQuery(mDataBase, Video::class.java)
                .build()
            withContext(Dispatchers.Main) {
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
        }, 500)
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    override fun onItemClick(position: Int, view: View) {
        var isFav = false
        var isSave = false
        val id = view.id
        when (id) {
            R.id.ivComment -> {
                loadComments(position)
            }
            R.id.civUser -> {
                transferData(position)
            }
            R.id.ivFavorite -> {
                isLogIn(position)
            }
        }
        /// TODO:Click item Not use position
        adapter.itemVideoBinding.ivSave.apply {
            setOnClickListener {
                if (isSave) {
                    isSave = false
                    adapter.itemVideoBinding.ivSave.setImageResource(R.drawable.save32)
                } else {
                    isSave = true
                    adapter.itemVideoBinding.ivSave.setImageResource(R.drawable.save_yellow)
                }
            }
        }






        adapter.itemVideoBinding.ivSearch.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFM_to_searchFM)
            }
        }
        adapter.itemVideoBinding.tvForU.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_followingFM_to_homeFM)
            }
        }

        adapter.itemVideoBinding.ivShare.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFM_to_shareBottomSheetFM)

            }
        }


    }

    fun updateFavorite(video: Video) {
        val dbVideoFavorite = Firebase.database.getReference("videos").child(video.uidVideo!!)
            .child("favorites")
        val dbVideo = Firebase.database.getReference("videos").child(video.uidVideo!!)
        dbVideoFavorite.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (element in snapshot.children) {
                    val favorite = element.getValue(Favorite::class.java)!!
                    if (listFavorite.contains(favorite)) {
//                        listFavorite.remove(favorite)
//                        val countHearts = listFavorite.size
//                        updateHeartVideoData(countHearts, dbVideo = dbVideo)
//                        adapter.itemVideoBinding.ivFavorite.setImageResource(R.drawable.heart_red)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {


            }
        })
    }

    fun clickIvFavorite(video: Video) {
        val dbUser = Firebase.database.getReference("users")

        dbUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (element in snapshot.children) {
                    /// TODO: Use snapshot.children and ref uuid of child to get uuid
                    val uuid = element.child("uuid").getValue(String::class.java)!!
                    /// TODO: Compare equal with currentUser of auth
                    if (auth.currentUser!!.uid == uuid) {
                        /// TODO: Ref dbUser use ValueEvent
                        dbUser.child(element.key!!).addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(snapshotUser: DataSnapshot) {
                                /// TODO: Get user from element.key and compare with uuid
                                val user = snapshotUser.getValue(User::class.java)!!
                                val favorite = Favorite(
                                    heart = true,
                                    users = user
                                )
                                /// TODO: Ref dbFavorite , dbVideo
                                val dbFavorite = Firebase.database.getReference("favorites")
                                val dbVideo =
                                    Firebase.database.getReference("videos").child(video.uidVideo!!)
                                val dbVideoFavorite = dbVideo.child("favorites")



                                dbFavorite.addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.exists()) {
                                            /// TODO: Remove favorite in dbFavorite when dislike
                                            dbFavorite.removeValue()
                                            /// TODO: Remove favorite in db when dislike
                                            dbVideoFavorite.removeValue()
                                            if (listFavorite.contains(favorite)) {
                                                listFavorite.remove(favorite)
                                                val countHearts = listFavorite.size
                                                updateHeartVideoData(countHearts, dbVideo = dbVideo)
                                            }

                                        } else {
                                            dbVideo.addListenerForSingleValueEvent(object :ValueEventListener{
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                   snapshot.children.forEach {
                                                       dbVideoFavorite.child(favorite.users!!.uuid!!)
                                                           .setValue(favorite)
                                                       if (!listFavorite.contains(favorite)) {
                                                           listFavorite.add(0,favorite)
                                                           val countHearts = listFavorite.size
                                                           updateHeartVideoData(countHearts, dbVideo = dbVideo)
                                                           updateFavorite(video)
//                        adapter.itemVideoBinding.ivFavorite.setImageResource(R.drawable.heart_red)
                                                       }

                                                   }
                                                }
                                                override fun onCancelled(error: DatabaseError) {
                                                }
                                            })
                                            val key = dbFavorite.push().key
                                            dbFavorite.child(key!!).setValue(favorite)
                                            updateFavorite(video)

                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }
                                })
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }
                        })

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    private fun updateHeartVideoData(countHearts: Int, dbVideo: DatabaseReference) {
        var hashMap: MutableMap<String, Int> = HashMap()
        hashMap.put("countHearts", countHearts)
        dbVideo.updateChildren(hashMap as Map<String, Int>)
    }

    private fun transferData(position: Int) {
        val video = adapter.getItem(position)
        navDicretion(video.user!!)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        checkComeIn(false)
        binding == null
    }

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.setBackgroundResource(R.color.black)
            navBot.tabColorSelected = ContextCompat.getColor(requireContext(), R.color.white)

        }
    }

    fun navDicretion(user: User) {
        if (activity?.findNavController(R.id.fmNavHostGraph)!!.currentDestination!!.id == R.id.followingFM) {
            val action = FollowingFMDirections.actionFollowingFMToDetailUserFM(user)
            activity?.findNavController(R.id.fmNavHostGraph)!!
                .lifeCycleNavigate(lifecycleScope, action)
//            findNavController().navigate(action)
//            findNavController().popBackStack(R.id.detailUserFM,false,true)
        }
    }

    fun NavController.lifeCycleNavigate(
        lifecycle: LifecycleCoroutineScope,
        navDirections: NavDirections
    ) =
        lifecycle.launchWhenResumed {
            navigate(navDirections)
        }

    private fun isLogIn(position: Int) {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            val video = adapter.getItem(position)
            clickIvFavorite(video)
            updateFavorite(video)
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