package team.tiktok.tiktokapp.fragments.forgotpass

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
//import me.ibrahimsn.lib.SmoothBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentForgotPaswordEmailBinding
import team.tiktok.tiktokapp.databinding.FragmentSigninEmailBinding


class ForgotWithEmailFM : Fragment() {
   lateinit var binding: FragmentForgotPaswordEmailBinding
   lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPaswordEmailBinding.inflate(layoutInflater)
        clickButton()
        navController = findNavController()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_forgotWithEmailFM_to_signInContainerFM2)

            }
        }
        binding.btnSend.apply {
            setOnClickListener {
                resetPassEmail()

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

    private  fun resetPassEmail(){
        val emailAddress = binding.edtEmail.text.toString().trim()
        if(TextUtils.isEmpty(emailAddress)){
            Toast.makeText(requireContext(), "Vui lòng nhập email.", Toast.LENGTH_SHORT).show()
            return
        }

        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_forgotWithEmailFM_to_signInContainerFM2)
                    Toast.makeText(requireContext(), "Reset thành công", Toast.LENGTH_SHORT).show()
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}