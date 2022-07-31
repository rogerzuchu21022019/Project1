package team.tiktok.tiktokapp.fragments.home

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
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.home.HomeVideoAdapter
import team.tiktok.tiktokapp.data.Favorite
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentHomeBinding
import team.tiktok.tiktokapp.fragments.following.FollowingFMDirections


class HomeFM : Fragment(), HomeVideoAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeVideoAdapter
    lateinit var auth: FirebaseAuth
    lateinit var mDataBase: DatabaseReference
    lateinit var dbVideos: DatabaseReference
    val listFavorite = mutableListOf<Favorite>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        checkComeIn(true)
        auth = Firebase.auth
        loadData()
        return binding.root
    }


    private fun loadData() {
        var handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(Runnable {
            binding.wait.visibility = View.GONE
            mDataBase = Firebase.database.getReference("videos")
            val options = FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mDataBase, Video::class.java)
                .build()
            adapter = HomeVideoAdapter(options)
            binding.vpHome.adapter = adapter
            adapter.setOnClickItem(this@HomeFM)
        }, 500)

    }

    override fun onStart() {
        super.onStart()
        var handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(Runnable {
            adapter.startListening()
        }, 500)
    }



    override fun onItemClick(position: Int, view: View) {
        var isFav = false
        var isSave = false
        /// TODO:Click item Use position
        val id = view.id
        when (id) {
            R.id.ivComment -> {
                loadComments(position = position)
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
        adapter.itemVideoBinding.tvFollowing.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFM_to_followingFM)
            }
        }

        adapter.itemVideoBinding.ivShare.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFM_to_shareBottomSheetFM)

            }
        }

    }

    fun clickIvFavorite(video: Video) {
        val dbUser = Firebase.database.getReference("users")

        dbUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (element in snapshot.children) {
                    /// TODO: Use snapshot.children and ref uuid of child to get uuid
                    val uuid = element.child("uuid").getValue(String::class.java)!!
                    /// TODO: Compare equal with currentUser of auth
                    if (auth.currentUser!!.uid == uuid) {
                        /// TODO: Ref dbUser use ValueEvent
                        dbUser.child(element.key!!).addListenerForSingleValueEvent(object :
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
                                            dbVideo.addListenerForSingleValueEvent(object :
                                                ValueEventListener {
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    snapshot.children.forEach {
                                                        dbVideoFavorite.child(favorite.users!!.uuid!!)
                                                            .setValue(favorite)
                                                        if (!listFavorite.contains(favorite)) {
                                                            listFavorite.add(0,favorite)
                                                            val countHearts = listFavorite.size
                                                            updateHeartVideoData(countHearts, dbVideo = dbVideo)
                                                        }

                                                    }
                                                }
                                                override fun onCancelled(error: DatabaseError) {
                                                }
                                            })
                                            val key = dbFavorite.push().key
                                            dbFavorite.child(key!!).setValue(favorite)
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


    private fun updateDataVideo(countSaved: Any) {
        var hashMap: MutableMap<String, Any> = HashMap()
        hashMap.put("countSaved", countSaved)

        dbVideos = Firebase.database.getReference("videos")
        dbVideos.updateChildren(hashMap as Map<String, Any>)
    }

    fun navDirection(user: User) {
        if (activity?.findNavController(R.id.fmNavHostGraph)!!.currentDestination!!.id == R.id.homeFM) {
            val action = HomeFMDirections.actionHomeFMToDetailUserFM(user)
            activity?.findNavController(R.id.fmNavHostGraph)!!
                .lifeCycleNavigate(lifecycleScope, action)
        }
    }


    fun loadComments(position: Int) {
        val video = adapter.getItem(position)
        val action = HomeFMDirections.actionHomeFMToCommentBottomSheetFM(video)
        findNavController().navigate(action)
    }


    private fun transferData(position: Int) {
        val video = adapter.getItem(position)
        navDirection(video.user!!)
    }

    fun NavController.lifeCycleNavigate(
        lifecycle: LifecycleCoroutineScope,
        navDirections: NavDirections
    ) =
        lifecycle.launchWhenResumed {
            navigate(navDirections)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        checkComeIn(false)
        binding == null
    }

    private fun isLogIn(position: Int) {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            val video = adapter.getItem(position)
            clickIvFavorite(video)
        } else {
            val action = FollowingFMDirections.actionFollowingFMToSignUpBottomSheetFM()
            findNavController().navigate(action)
        }
    }

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.setBackgroundResource(R.color.black)
            navBot.tabColorSelected = ContextCompat.getColor(requireContext(), R.color.white)
            navBot.badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

}