package team.tiktok.tiktokapp.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentManageAccountBinding


class ManageAccountFM : Fragment() {
   lateinit var binding:FragmentManageAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageAccountBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_manageAccountFM_to_settingAndPrivacyFM)
            }
        }
    }


}