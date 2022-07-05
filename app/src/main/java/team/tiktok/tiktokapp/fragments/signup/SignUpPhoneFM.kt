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
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSignupBirthBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupEmailBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupPhoneBinding
import java.util.*


class SignUpPhoneFM : Fragment() {
    lateinit var binding: FragmentSignupPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupPhoneBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.btnSendCode.apply {
            setOnClickListener {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}