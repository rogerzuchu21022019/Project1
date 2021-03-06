package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import team.tiktok.tiktokapp.databinding.FragmentSignUpCreatePassBinding


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
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().popBackStack()
            }
        }
        binding.btnNext.apply {

            setOnClickListener {
                if(TextUtils.isEmpty(binding.edtPassword.text.toString())){
                    Toast.makeText(requireContext(),"Please Fill Information",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val password = binding.edtPassword.text.toString().trim()
                val list =getArrSignUp().toMutableList()
                list.add(2,password)
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