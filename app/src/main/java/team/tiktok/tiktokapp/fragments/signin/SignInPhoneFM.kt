package team.tiktok.tiktokapp.fragments.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import team.tiktok.tiktokapp.databinding.FragmentSigninPhoneBinding
import team.tiktok.tiktokapp.databinding.FragmentSignupPhoneBinding


class SignInPhoneFM : Fragment() {
    lateinit var binding: FragmentSigninPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninPhoneBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.btnSendCode.apply {
            setOnClickListener {
                Toast.makeText(requireContext(),"click1",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}