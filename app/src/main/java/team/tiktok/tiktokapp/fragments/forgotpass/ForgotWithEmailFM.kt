package team.tiktok.tiktokapp.fragments.forgotpass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.ibrahimsn.lib.SmoothBottomBar
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
                findNavController().navigate(R.id.action_forgotWithEmailFM_to_signInContainerFM2)
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
    }
}