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
import team.tiktok.tiktokapp.databinding.FragmentSignUpCreateToptopIdBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupBirthBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupEmailBinding
import java.util.*


class SignUpCreateTopTopIDFM : Fragment() {
    lateinit var binding: FragmentSignUpCreateToptopIdBinding
    val navArg: SignUpCreateTopTopIDFMArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCreateToptopIdBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }

    fun getArrSignUp(): Array<String> {
        return navArg.arrSignUp
    }

    private fun clickButton() {
        binding.btnCreate.apply {
            setOnClickListener {
                val topTopID = binding.edtTopTopID.text.toString().trim()
                val list = getArrSignUp().toMutableList()
                list.add(3,topTopID)

                val birth = list[0]
                val email = list[1]
                val password = list[2]
                val topTopId = list[3]

                val action = SignUpCreateTopTopIDFMDirections.actionSignUpCreateTopTopIDFMToAddFM()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}