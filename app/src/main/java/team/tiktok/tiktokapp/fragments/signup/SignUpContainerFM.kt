package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import me.ibrahimsn.lib.SmoothBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.signup.SignUpViewpagerAdapter
import team.tiktok.tiktokapp.databinding.FragmentSignUpContainerBinding


class SignUpContainerFM : Fragment() {
   lateinit var binding: FragmentSignUpContainerBinding
    lateinit var adapter:SignUpViewpagerAdapter
    val navFMArgs:SignUpContainerFMArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkComeIn(true)
        binding = FragmentSignUpContainerBinding.inflate(layoutInflater)
        initViewPager()
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                val action = SignUpContainerFMDirections.actionSignUpContainerFMToSignUpBirthFM()
                findNavController().navigate(action)
            }
        }
    }
    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tab,
            binding.vpSignUp
        ) { tab, position ->

            if (position == 0) {
                tab.text = "Phone"
            } else if (position == 1) {
                tab.text = "Email"
            }
        }.attach()
    }

    private fun initViewPager() {
        adapter = SignUpViewpagerAdapter(this)
        binding.vpSignUp.adapter = adapter
        initTabLayout()
    }

    private fun checkComeIn(isComeIn:Boolean){
        if (isComeIn){
            val navBot = requireActivity()!!.findViewById<SmoothBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        }else{
            val navBot = requireActivity()!!.findViewById<SmoothBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
        checkComeIn(false)
    }
}