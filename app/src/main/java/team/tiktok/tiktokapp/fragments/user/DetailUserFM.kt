package team.tiktok.tiktokapp.fragments.user

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.detail.DetailAdapter
import team.tiktok.tiktokapp.adapter.detail.DetailViewpagerAdapter
import team.tiktok.tiktokapp.databinding.FragmentDetailUserBinding


class DetailUserFM : Fragment(), DetailAdapter.OnClickItemInRecyclerView {
    lateinit var binding: FragmentDetailUserBinding
    lateinit var adapter: DetailViewpagerAdapter
    val navArgs:DetailUserFMArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater)
        checkComeIn(true)
        getUser()
        clickButton()
        initViewPager()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_detailUserFM_to_homeFM)
            }
        }
    }
    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tab,
            binding.vpDetail
        ) { tab, position ->
            when (position){
                0->{
                    tab.setIcon(R.drawable.list)
                }
                1->{
                    tab.setIcon(R.drawable.resource_private)

                }
                2->{
                    tab.setIcon(R.drawable.heart)

                }
                3->{
                    tab.setIcon(R.drawable.favourite)

                }
            }
        }.attach()
    }

    fun getUser(){
        CoroutineScope(SupervisorJob()).launch(Dispatchers.IO){
            binding.user = navArgs.user
        }
    }

    private fun initViewPager() {
        adapter = DetailViewpagerAdapter(this)
        binding.vpDetail.adapter = adapter
        initTabLayout()
    }

    private fun checkComeIn(isComeIn:Boolean){
        if (isComeIn){
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        }else{
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        binding == null
        checkComeIn(false)
    }
//

    override fun onItemClick(position: Int, view: View) {
        val id = view.id
        when (id) {
            R.id.videoView -> {
                Toast.makeText(requireContext(), "ohm $position", Toast.LENGTH_SHORT).show()
            }
        }
    }

}