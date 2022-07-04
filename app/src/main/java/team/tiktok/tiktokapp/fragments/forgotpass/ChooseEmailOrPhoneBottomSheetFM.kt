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

class ChooseEmailOrPhoneBottomSheetFM : BottomSheetDialogFragment(), View.OnClickListener  {
    lateinit var binding: FragmentChoosePhoneEmailBottomSheetBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChoosePhoneEmailBottomSheetBinding.inflate(layoutInflater)
//        clickButton()
        return binding!!.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme1
    }


    override fun onClick(view: View?) {
        val id = view?.id
        when (id){
            R.id.tvSignIn -> {
                findNavController().navigate(R.id.action_profileBottomSheetFM_to_settingAndPrivacyFM)
            }
            R.id.ivClose ->{
                this.dismiss()
            }
            R.id.tvPhoneMailTiktok ->{


            }
        }
    }


}