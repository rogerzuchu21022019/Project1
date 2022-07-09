package team.tiktok.tiktokapp.fragments.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import team.tiktok.tiktokapp.databinding.FragmentFriendBinding


class FriendFM : Fragment(){
    lateinit var binding: FragmentFriendBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }


}