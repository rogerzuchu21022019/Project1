package team.tiktok.tiktokapp.fragments.following

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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


    override fun onItemClick(position: Int, view: View) {
        var isSave = false
        val id = view.id
        when (id) {
            R.id.ivComment -> {
                navToComment(position)
            }
            R.id.civUser -> {
                navToDetailUser(position)
            }
            R.id.ivFavorite -> {
                val ivFav = view.findViewById<ImageView>(R.id.ivFavorite)
                isLogIn(position, ivFav)
            }
            R.id.ivSave -> {
                val ivSave = view.findViewById<ImageView>(R.id.ivSave)
//                isLogIn(position, ivSave)
            }
        }
        /// TODO:Click item Not use position

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
                findNavController().navigate(R.id.action_followingFM_to_shareBottomSheetFM)
            }
        }
    }

    fun clickIvFavorite(video: Video) {
        val dbUser = Firebase.database.getReference("users")
        val uuid = auth.currentUser!!.uid
        dbUser.child(uuid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshotUser: DataSnapshot) {
                    /// TODO: Get user from element.key and compare with uuid
                    val user = snapshotUser.getValue(User::class.java)!!
                    user.videos = video

                    val favorite = Favorite(
                        heart = true,
                        users = user
                    )
                    /// TODO: Ref dbFavorite , dbVideo
                    val dbVideo = Firebase.database.getReference("videos")
                        .child(video.uidVideo!!)
                    val dbVideoFavorite = dbVideo.child("favorites")

                    dbVideoFavorite.addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (!snapshot.hasChild(uuid)) {
                                dbVideoFavorite.child(uuid).setValue(favorite)
                                val countHearts = snapshot.childrenCount.toInt()+1
                                updateHeartVideoData(countHearts, dbVideo = dbVideo)
                            } else {
                                /// TODO: Remove favorite in db when dislike
                                dbVideoFavorite.child(user.uuid!!).removeValue()
                                val countHearts = snapshot.childrenCount.toInt()-1
                                updateHeartVideoData(countHearts, dbVideo = dbVideo)
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


    private fun updateHeartVideoData(countHearts: Int, dbVideo: DatabaseReference) {
        var hashMap: MutableMap<String, Int> = HashMap()
        hashMap.put("countHearts", countHearts)
        dbVideo.updateChildren(hashMap as Map<String, Int>)
    }

    private fun navToDetailUser(position: Int) {
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
        }
    }

    fun NavController.lifeCycleNavigate(
        lifecycle: LifecycleCoroutineScope,
        navDirections: NavDirections
    ) =
        lifecycle.launchWhenResumed {
            navigate(navDirections)
        }

    private fun isLogIn(position: Int, ivFav: ImageView) {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            val video = adapter.getItem(position)
            clickIvFavorite(video)

        } else {
            val action = FollowingFMDirections.actionFollowingFMToSignUpBottomSheetFM()
            findNavController().navigate(action)
        }
    }

    fun navToComment(position: Int) {
        val video = adapter.getItem(position)
        val action = FollowingFMDirections.actionFollowingFMToCommentBottomSheetFM(video)
        findNavController().navigate(action)
    }
}