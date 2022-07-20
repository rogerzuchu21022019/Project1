package team.tiktok.tiktokapp.fragments.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentInboxBinding


class InboxFM : Fragment() {
   lateinit var binding:FragmentInboxBinding
   lateinit var auth: FirebaseAuth
   lateinit var database:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInboxBinding.inflate(layoutInflater)
        isLogIn()
        checkComeIn(true)
        return binding.root
    }


    private fun navSignUp() {
        val action = InboxFMDirections.actionInboxFMToSignUpBottomSheetFM()
        findNavController().navigate(action)
    }

    private fun isLogIn() {
        auth = Firebase.auth
        if (auth.currentUser!=null){
            binding.constraintSecond.visibility = View.GONE
            binding.constraintMain.visibility = View.VISIBLE

        }else{
            binding.constraintMain.visibility = View.GONE
            binding.constraintSecond.visibility = View.VISIBLE
            navSignUp()
            binding.layoutBot.apply {
                setOnClickListener{
                    navSignUp()
                }
            }
            binding.layoutMid.apply {
                setOnClickListener{
                    navSignUp()
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
    private fun checkComeIn(isComeIn:Boolean){
        if (isComeIn){
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.setBackgroundResource(R.drawable.border_nav_bot)
            navBot.tabColorSelected = ContextCompat.getColor(requireContext(),R.color.black)
            navBot.badgeTextColor = ContextCompat.getColor(requireContext(),R.color.white)

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