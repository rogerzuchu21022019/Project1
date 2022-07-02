package team.tiktok.tiktokapp.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.VideoAdapter
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentDetailUserBinding
import team.tiktok.tiktokapp.databinding.FragmentExploreBinding
import team.tiktok.tiktokapp.databinding.FragmentHomeBinding
import team.tiktok.tiktokapp.databinding.FragmentProfileBinding


class DetailUserFM : Fragment() {
   lateinit var binding:FragmentDetailUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_detailUserFM_to_homeFM)
            }
        }

    }


}