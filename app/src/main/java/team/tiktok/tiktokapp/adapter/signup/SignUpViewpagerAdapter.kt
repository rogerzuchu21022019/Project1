package team.tiktok.tiktokapp.adapter.signup

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import team.tiktok.tiktokapp.fragments.signup.SignUpEmailFM
import team.tiktok.tiktokapp.fragments.signup.SignUpPhoneFM

class SignUpViewpagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignUpPhoneFM()
            1 -> SignUpEmailFM()
            else -> SignUpPhoneFM()
        }

    }

}