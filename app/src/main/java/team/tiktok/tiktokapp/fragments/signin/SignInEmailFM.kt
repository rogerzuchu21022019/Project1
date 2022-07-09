package team.tiktok.tiktokapp.fragments.signin

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import me.ibrahimsn.lib.SmoothBottomBar
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSigninEmailBinding
import team.tiktok.tiktokapp.fragments.inbox.InboxFM
import team.tiktok.tiktokapp.fragments.profile.ProfileFM


class SignInEmailFM : Fragment() {
   lateinit var binding: FragmentSigninEmailBinding
   lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninEmailBinding.inflate(layoutInflater)
        clickButton()
        auth = Firebase.auth
        checkComeIn(true)
        return binding.root
    }

    private fun clickButton() {
        binding.btnSignIn.apply {
            setOnClickListener {

                val email = binding.edtEmail.text.toString().trim()
                val password = binding.edtPassword.text.toString().trim()
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(requireContext(),"Please Fill Information", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        Toast.makeText(requireContext(),"SignIn OK", Toast.LENGTH_SHORT).show()
                        if (auth.currentUser!=null){
                             val id = findNavController().previousBackStackEntry!!.destination.id
                            if(id==R.id.inboxFM){
                                val action  = SignInContainerFMDirections.actionSignInContainerFMToInboxFM()
                                findNavController().navigate(action)
                            }else if(id==R.id.profileFM){
                                val action  = SignInContainerFMDirections.actionSignInContainerFMToProfileFM()
                                findNavController().navigate(action)
                            }


                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(),"SignIn Fail", Toast.LENGTH_SHORT).show()
                    }

            }

        }
        binding.tvForgotPassword.apply {
            setOnClickListener {
                Toast.makeText(requireContext(),"click1", Toast.LENGTH_SHORT).show()
                val action  = SignInContainerFMDirections.actionSignInContainerFMToChooseEmailOrPhoneBottomSheetFM()
                findNavController().navigate(action)
            }
        }
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