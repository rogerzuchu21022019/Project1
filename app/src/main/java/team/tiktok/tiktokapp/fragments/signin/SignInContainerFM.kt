package team.tiktok.tiktokapp.fragments.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
//import me.ibrahimsn.lib.SmoothBottomBar
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.signin.SignInViewpagerAdapter
import team.tiktok.tiktokapp.databinding.FragmentSignInContainerBinding


class SignInContainerFM : Fragment() {
    lateinit var binding: FragmentSignInContainerBinding
    lateinit var adapter: SignInViewpagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInContainerBinding.inflate(layoutInflater)
        checkComeIn(true)
        initViewPager()
        clickButton()
        return binding.root
    }

    private fun clickButton() {

        binding.ivBack.apply {
            setOnClickListener {

            }
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tab,
            binding.vpSignIn
        ) { tab, position ->

            if (position == 0) {
                tab.text = "Phone"
            } else if (position == 1) {
                tab.text = "Email/TopTop ID"
            }
        }.attach()
    }

    private fun initViewPager() {
        adapter = SignInViewpagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        binding.vpSignIn.adapter = adapter
        initTabLayout()
    }

    private fun checkComeIn(isComeIn:Boolean){
        if (isComeIn){
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        }else{
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
        checkComeIn(false)
    }


}