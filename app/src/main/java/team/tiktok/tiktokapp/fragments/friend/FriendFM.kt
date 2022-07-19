package team.tiktok.tiktokapp.fragments.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentFriendBinding


class FriendFM : Fragment(){
    lateinit var binding: FragmentFriendBinding
    lateinit var auth :FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendBinding.inflate(layoutInflater)
        checkComeIn(true)
        isLogIn()
        return binding.root
    }
    /// TODO: Check signed in or not yet

    private fun isLogIn() {
        auth = Firebase.auth
        /// TODO: Check signed in
        if (auth.currentUser!=null){
//            checkExist(auth.currentUser!!.uid)
            binding.constraintSecond.visibility = View.GONE
            binding.constraintMain.visibility = View.VISIBLE

        }else{
            /// TODO: Check not signed in
            binding.constraintMain.visibility = View.GONE
            binding.constraintSecond.visibility = View.VISIBLE
            navSignUp()
            binding.layoutMid.apply {
                setOnClickListener{
                    navSignUp()
                }
            }
            binding.layoutBottom.apply {
                setOnClickListener{
                    navSignUp()
                }
            }
        }
    }
    private fun navSignUp() {
        val action = FriendFMDirections.actionFriendFMToSignUpBottomSheetFM()
        findNavController().navigate(action)
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
    }


}