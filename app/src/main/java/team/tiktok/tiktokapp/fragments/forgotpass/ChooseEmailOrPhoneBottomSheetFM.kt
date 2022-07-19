package team.tiktok.tiktokapp.fragments.forgotpass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentChoosePhoneEmailBottomSheetBinding
import team.tiktok.tiktokapp.databinding.FragmentProfileBottomSheetBinding
import team.tiktok.tiktokapp.databinding.FragmentSignUpMainBinding

class ChooseEmailOrPhoneBottomSheetFM : BottomSheetDialogFragment() ,View.OnClickListener {
    lateinit var binding: FragmentChoosePhoneEmailBottomSheetBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChoosePhoneEmailBottomSheetBinding.inflate(layoutInflater)
        clickButton()
        return binding!!.root
    }

    private fun clickButton() {
        binding.tvPhone.setOnClickListener(this)
        binding.tvEmail.setOnClickListener(this)
        binding.tvCancel.setOnClickListener(this)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme1
    }



    override fun onDestroyView() {
        super.onDestroyView()

        binding == null
    }

    override fun onClick(p0: View?) {
        val id = p0!!.id
        when(id){
            R.id.tvEmail -> {
                val action = ChooseEmailOrPhoneBottomSheetFMDirections.actionChooseEmailOrPhoneBottomSheetFMToForgotWithEmailFM()
                findNavController().navigate(action)
            }
            R.id.tvPhone -> {
                val action = ChooseEmailOrPhoneBottomSheetFMDirections.actionChooseEmailOrPhoneBottomSheetFMToForgotWithPhoneFM()
                findNavController().navigate(action)
            }
            R.id.tvCancel -> {
                this.dismiss()
            }
        }
    }

}