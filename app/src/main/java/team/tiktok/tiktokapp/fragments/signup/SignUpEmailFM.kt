package team.tiktok.tiktokapp.fragments.signup

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSignupBirthBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupEmailBinding
import java.util.*


class SignUpEmailFM : Fragment() {
   lateinit var binding: FragmentSignupEmailBinding
    val navArgs :SignUpContainerFMArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupEmailBinding.inflate(layoutInflater)
        clickButton()
        getBirth()
        return binding.root
    }

    private fun getBirth():String {
        return  navArgs.birth
    }


    private fun clickButton() {
        binding.chkSignUp.isChecked = true
        binding.btnSignUp.apply {
            setOnClickListener {
                val action = SignUpContainerFMDirections.actionSignUpContainerFMToSignUpCreatePassFM()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}