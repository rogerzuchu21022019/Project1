package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSignUpCreatePassBinding


class SignUpCreatePassFM : Fragment() {
   lateinit var binding: FragmentSignUpCreatePassBinding
    val navArgs :SignUpCreatePassFMArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCreatePassBinding.inflate(layoutInflater)
        handleEditText()
        clickButton()
        checkComeIn(true)
        return binding.root
    }
    fun getArrSignUp():Array<String>{
        return navArgs.arrSignUp
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.btnNext.apply {
            setOnClickListener {
                val regex = "(?=[A-Za-z0-9@#\$%^&+!=]+\$)^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+!=])(?=.{6,}).*\$".toRegex()
                if(TextUtils.isEmpty(binding.edtPassword.text.toString())){
                    return@setOnClickListener
                }
                val password = binding.edtPassword.text.toString().trim()
                if (!password.matches(regex)){
                    return@setOnClickListener
                }
                val list =getArrSignUp().toMutableList()
                list.add(2,password)
                val action = SignUpCreatePassFMDirections.actionSignUpCreatePassFMToSignUpCreateTopTopIDFM(list.toTypedArray())
                findNavController().navigate(action)
            }
        }

    }
    fun handleEditText(){
        binding.edtPassword.doAfterTextChanged {
            /// TODO: Regex 1 uppercase,1 special,
            val regex = "(?=[A-Za-z0-9@#\$%^&+!=]+\$)^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+!=])(?=.{6,20}).*\$".toRegex()
            if(TextUtils.isEmpty(binding.edtPassword.text.toString())){
                return@doAfterTextChanged
            }
            val password = binding.edtPassword.text.toString().trim()

            if (password.length< 6 || password.length>20 ){
                binding.chk1.isChecked = false
                binding.chk2.isChecked = false
                return@doAfterTextChanged
            }else{
                binding.chk1.isChecked =true
            }

            if (!password.matches(regex)){
                return@doAfterTextChanged
            }else{
                binding.chk1.isChecked =true
                binding.chk2.isChecked = true
            }

            binding.chk1.isChecked =true
            binding.chk2.isChecked = true

        }
    }
    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        } else {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
        checkComeIn(false)

    }
}