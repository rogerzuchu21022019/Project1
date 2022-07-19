package team.tiktok.tiktokapp.fragments.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentEmptyBinding


class EmptyFM : Fragment() {
   lateinit var binding: FragmentEmptyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmptyBinding.inflate(layoutInflater)
        navDirection()
        return binding.root
    }

    private fun navDirection() {
        var handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(Runnable {
            findNavController().navigate(R.id.action_emptyFM_to_homeFM)
            Log.d("ChangeScreen","success")
        },300)




    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

}