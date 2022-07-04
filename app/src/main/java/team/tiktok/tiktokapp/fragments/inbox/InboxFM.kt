package team.tiktok.tiktokapp.fragments.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import team.tiktok.tiktokapp.databinding.FragmentInboxBinding


class InboxFM : Fragment() {
   lateinit var binding:FragmentInboxBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInboxBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}