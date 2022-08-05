package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSignupEmailBinding


class SignUpEmailFM : Fragment() {
   lateinit var binding: FragmentSignupEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupEmailBinding.inflate(layoutInflater)
        checkComeIn(true)
        return binding.root
    }

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        } else {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        checkComeIn(false)
        binding == null

    }
}