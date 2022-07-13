package team.tiktok.tiktokapp.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
//import me.ibrahimsn.lib.SmoothBottomBar
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentLikedVideosBinding
import team.tiktok.tiktokapp.databinding.FragmentPrivateVideosBinding
import team.tiktok.tiktokapp.databinding.FragmentSavedVideosBinding
import team.tiktok.tiktokapp.databinding.FragmentSigninEmailBinding


class SavedUserVideosFM : Fragment() {
   lateinit var binding: FragmentSavedVideosBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedVideosBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }

    private fun clickButton() {

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
    }

}