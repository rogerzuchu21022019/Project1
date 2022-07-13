package team.tiktok.tiktokapp.fragments.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.detail.DetailUserViewpagerAdapter
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentProfileBinding


class ProfileFM : Fragment() {
    private val IMAGE_REQ = 1
    private var imagePath: Uri? = null
    lateinit var binding: FragmentProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var adapter: DetailUserViewpagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        checkComeIn(true)
        clickImage()
        initViewPager()
        setBackStackStartDestinationID()
        clickButton()
        isLogIn()
        return binding.root
    }


    private fun navSignUp() {
        val action = ProfileFMDirections.actionProfileFMToSignUpBottomSheetFM()
        findNavController().navigate(action)
    }

    ///check loged in or not yet
    private fun isLogIn() {
        auth = Firebase.auth
        if (auth.currentUser != null) {
//            checkExist(auth.currentUser!!.uid)
            binding.layoutSecond.visibility = View.GONE
            binding.layoutMain.visibility = View.VISIBLE
        } else {
            CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
                navSignUp()
                binding.layoutSecond.visibility = View.VISIBLE
                binding.layoutMain.visibility = View.GONE
                binding.ivList1.apply {
                    setOnClickListener {
                        val action = ProfileFMDirections.actionProfileFMToSettingAndPrivacyFM()
                        findNavController().navigate(action)
                    }
                }
                binding.layoutMain.apply {
                    setOnClickListener {
                        navSignUp()
                    }
                }
                binding.layoutSecond.apply {
                    setOnClickListener {
                        navSignUp()
                    }
                }
            }
        }
    }

    private fun checkExist(uid: String) {
        database = Firebase.database.getReference("users")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    snapshot.children.forEach {
                        var user: User = it.getValue(User::class.java)!!
                        if (uid == user.uuid) {
                            updateUI(user)
                            Toast.makeText(
                                requireContext(),
                                "ok ${user.topTopID}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        database.child("videos")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (element in snapshot.children) {
                            var video = element.getValue(Video::class.java)

                            Toast.makeText(
                                requireContext(),
                                "ok ${video!!.url}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }


                override fun onCancelled(error: DatabaseError) {

                }

            })

    }

    private fun updateUI(user: User) {
        binding.user = user
    }

    private fun setBackStackStartDestinationID() {
        findNavController().graph.setStartDestination(R.id.inboxFM)
    }


    private fun clickButton() {
        binding.btnEditProfile.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_profileFM_to_editProfileFM)
            }
        }
        binding.ivList.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_profileFM_to_profileBottomSheetFM)
            }
        }
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
                3 -> {
                    tab.setIcon(R.drawable.favorite_16x)
                }
            }
        }.attach()
    }

    private fun initViewPager() {
        adapter = DetailUserViewpagerAdapter(this)
        binding.vpDetail.adapter = adapter
        initTabLayout()

    }

    private fun clickImage() {
        binding.civAvatar.apply {
            setOnClickListener {
                requestPermission()
                Toast.makeText(requireActivity(), "OK", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), IMAGE_REQ
            )
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*" // if you want to you can use pdf/gif/video
        someActivityResultLauncher.launch(intent)
    }


    private var someActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data
                imagePath = data!!.data
                Picasso.get().load(imagePath).into(binding.civAvatar)

            }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.setBackgroundResource(R.drawable.border_nav_bot)
            navBot.tabColorSelected = ContextCompat.getColor(requireContext(), R.color.black)
            navBot.badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)

        }
//        else{
//            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
//            navBot.setBackgroundResource(R.drawable.border_nav_bot)
//            navBot.tabColorSelected = ContextCompat.getColor(requireContext(),R.color.black)
//
//
//        }
    }


}