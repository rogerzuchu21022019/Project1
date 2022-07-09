package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSignUpMainBinding

class SignUpMainBottomSheetFM : BottomSheetDialogFragment(), View.OnClickListener {
    lateinit var binding: FragmentSignUpMainBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpMainBinding.inflate(layoutInflater)
        clickButton()
        return binding!!.root
    }

    private fun clickButton() {
        binding.tvPhoneMailTiktok.setOnClickListener(this)
        binding.ivClose.setOnClickListener(this)
        binding.tvSignIn.setOnClickListener(this)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme1
    }


    override fun onClick(view: View?) {
        val id = view?.id
        when (id) {
            R.id.tvPhoneMailTiktok -> {
                val action = SignUpMainBottomSheetFMDirections.actionSignUpBottomSheetFMToSignUpBirthFM()
                findNavController().navigate(action)
            }
            R.id.ivClose -> {
                this.dismiss()
            }
            R.id.tvSignIn->{
                val action = SignUpMainBottomSheetFMDirections.actionSignUpBottomSheetFMToSignInContainerFM()
                findNavController().navigate(action)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

}