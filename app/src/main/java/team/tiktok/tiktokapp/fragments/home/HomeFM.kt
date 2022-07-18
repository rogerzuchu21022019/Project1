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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.home.HomeVideoAdapter
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentHomeBinding


class HomeFM : Fragment(), HomeVideoAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeVideoAdapter
    lateinit var auth: FirebaseAuth
    lateinit var mDataBase: DatabaseReference
    lateinit var dbVideos: DatabaseReference


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

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    override fun onItemClick(position: Int, view: View) {
        var isFav = false
        var isSave = false
        /// TODO:Click item Use position
        val id = view.id
        when (id) {
            R.id.ivComment -> {
                isLogIn(position)
            }
            R.id.civUser->{
                transferData(position)
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



        adapter.itemVideoBinding.ivFavorite.apply {
            setOnClickListener {

                if (isFav) {
                    adapter.itemVideoBinding.ivFavorite.setImageResource(R.drawable.heart_white)
                    isFav = false
                } else {
                    adapter.itemVideoBinding.ivFavorite.setImageResource(R.drawable.heart_red)
                    isFav = true
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

    private fun isLogIn(position: Int) {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            loadComments(position = position)
        } else {
            val action = HomeFMDirections.actionHomeFMToSignUpBottomSheetFM()
            findNavController().navigate(action)
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

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.setBackgroundResource(R.color.black)
            navBot.tabColorSelected = ContextCompat.getColor(requireContext(), R.color.white)
            navBot.badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

}