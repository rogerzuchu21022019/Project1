package team.tiktok.tiktokapp.fragments.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentFriendBinding


class FriendFM : Fragment(){
    lateinit var binding: FragmentFriendBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendBinding.inflate(layoutInflater)
        checkComeIn(true)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        checkComeIn(false)
        binding == null
    }
    private fun checkComeIn(isComeIn:Boolean){
        if (isComeIn){
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.setBackgroundResource(R.color.black)
            navBot.tabColorSelected = ContextCompat.getColor(requireContext(), R.color.white)
            navBot.badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)


        }
//        else{
//            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
//            navBot.setBackgroundResource(R.drawable.border_nav_bot)
//            navBot.tabColorSelected = ContextCompat.getColor(requireContext(), R.color.black)
//            navBot.badgeTextColor = ContextCompat.getColor(requireContext(), R.color.black)
//
//
//        }
    }


}