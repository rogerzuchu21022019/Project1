package team.tiktok.tiktokapp.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSettingAndPrivacyBinding


class SettingAndPrivacyFM : Fragment() {
   lateinit var binding:FragmentSettingAndPrivacyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingAndPrivacyBinding.inflate(layoutInflater)
        clickButton()
        checkComeIn(true)
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_settingAndPrivacyFM_to_profileFM)
            }
        }
        binding.tvManageAccount.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_settingAndPrivacyFM_to_manageAccountFM)
            }
        }
//        binding.tvSignOut.apply {
//            setOnClickListener {
//                findNavController().navigate(R.id.action_settingAndPrivacyFM_to_homeFM)
//            }
//        }
    }

    private fun checkComeIn(isComeIn:Boolean){
        if (isComeIn){
            val navBot = requireActivity()!!.findViewById<BottomNavigationView>(R.id.navBot)
            navBot.visibility = View.GONE
        }else{
            val navBot = requireActivity()!!.findViewById<BottomNavigationView>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        checkComeIn(false)

        binding == null
    }
}