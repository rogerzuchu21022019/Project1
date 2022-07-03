package team.tiktok.tiktokapp.fragments.profile

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
import team.tiktok.tiktokapp.databinding.FragmentProfileBottomSheetBinding

class ProfileBottomSheetFM : BottomSheetDialogFragment(), View.OnClickListener  {
    lateinit var binding: FragmentProfileBottomSheetBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBottomSheetBinding.inflate(layoutInflater)
//        clickButton()
        initClick()
        return binding!!.root
    }

    private fun initClick() {
        binding.tvToolPrivate.setOnClickListener(this)
    }

    //
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme1
    }

    private fun clickButton() {
        binding.tvToolForAuthor.apply {
            setOnClickListener {

            }
        }

        binding.tvToolPrivate.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_profileBottomSheetFM_to_settingAndPrivacyFM)
            }
        }
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id){
            R.id.tvToolPrivate -> {
                findNavController().navigate(R.id.action_profileBottomSheetFM_to_settingAndPrivacyFM)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}