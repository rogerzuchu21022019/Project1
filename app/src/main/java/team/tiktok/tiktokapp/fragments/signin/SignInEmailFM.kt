package team.tiktok.tiktokapp.fragments.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.ibrahimsn.lib.SmoothBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSigninEmailBinding


class SignInEmailFM : Fragment() {
   lateinit var binding: FragmentSigninEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninEmailBinding.inflate(layoutInflater)
        clickButton()
        checkComeIn(true)
        return binding.root
    }

    private fun clickButton() {
        binding.btnSignIn.apply {
            setOnClickListener {
                Toast.makeText(requireContext(),"click1", Toast.LENGTH_SHORT).show()
                val action  = SignInContainerFMDirections.actionSignInContainerFMToAddFM()
                findNavController().navigate(action)
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