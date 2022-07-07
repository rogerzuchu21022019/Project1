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
import team.tiktok.tiktokapp.databinding.FragmentSignUpCreatePassBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupBirthBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupEmailBinding
import java.util.*


class SignUpCreatePassFM : Fragment() {
   lateinit var binding: FragmentSignUpCreatePassBinding
    val navArgs :SignUpCreatePassFMArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCreatePassBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }
    fun getArrSignUp():Array<String>{
        return navArgs.arrSignUp
    }

    private fun clickButton() {
        binding.btnNext.apply {
            val password = binding.edtPassword.text.toString().trim()
            val list =getArrSignUp().toMutableList()
            list.add(2,password)
            setOnClickListener {

                val action = SignUpCreatePassFMDirections.actionSignUpCreatePassFMToSignUpCreateTopTopIDFM(list.toTypedArray())
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}