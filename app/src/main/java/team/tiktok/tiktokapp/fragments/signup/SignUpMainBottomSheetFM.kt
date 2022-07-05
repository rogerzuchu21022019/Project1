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
//        clickButton()
        return binding!!.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme1
    }


    override fun onClick(view: View?) {
        val id = view?.id
        when (id) {
            R.id.tvPhoneMailTiktok -> {
                findNavController().navigate(R.id.action_profileBottomSheetFM_to_settingAndPrivacyFM)
            }
            R.id.tvEmail -> {

            }
            R.id.tvCancel -> {
                this.dismiss()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

}