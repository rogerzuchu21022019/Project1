package team.tiktok.tiktokapp.fragments.user

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.detail.DetailAdapter
import team.tiktok.tiktokapp.adapter.detail.UserDetailVideosViewpagerAdapter
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.databinding.FragmentDetailUserBinding


class DetailUserFM : Fragment(), DetailAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentDetailUserBinding
    lateinit var adapter: UserDetailVideosViewpagerAdapter
    val navArgs : DetailUserFMArgs by navArgs()
    lateinit var mDataBase: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater)
        checkComeIn(true)
        loadDataFromHome()
        retrieveUser()
        clickButton()
        initViewPager()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_detailUserFM_to_homeFM)
            }
        }
        binding.btnEditProfile.apply {
            setOnClickListener {
            }
        }
    }
    fun loadDataFromHome():User{
        val user = navArgs.user
        binding.user = user
        binding.following = user.following
        binding.follower = user.follower
        return  user
    }

    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tab,
            binding.vpDetail
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.list_16px)
                }
                1 -> {
                    tab.setIcon(R.drawable.private_16px)
                }
                2 -> {
                    tab.setIcon(R.drawable.heart_16px)
                }
            }
        }.attach()
    }

    fun retrieveUser() {
        var auth = Firebase.auth
        CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                mDataBase = Firebase.database.getReference("videos")

                val handle = Handler(Looper.myLooper()!!)
                handle.postDelayed({
                    mDataBase.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                snapshot.children.forEach {
                                    val user1 = it.child("user").getValue(User::class.java)!!
                                    if (user1.topTopID!! == loadDataFromHome().topTopID) {
                                        binding.user = user1
                                        binding.follower = user1.follower
                                        binding.following = user1.following
                                    }
                                    Log.d("DetailUserFM ", "user : ${user1}")
                                }

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }, 500)
            }
        }
    }

    private fun initViewPager() {
        adapter = UserDetailVideosViewpagerAdapter(this)
        binding.vpDetail.adapter = adapter
        initTabLayout()
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


    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
        checkComeIn(false)
    }
//

    override fun onItemClick(position: Int, view: View) {
        val id = view.id
        when (id) {
            R.id.videoView -> {
                Toast.makeText(requireContext(), "ohm $position", Toast.LENGTH_SHORT).show()
            }
        }
    }

}