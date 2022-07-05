package team.tiktok.tiktokapp.adapter.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import team.tiktok.tiktokapp.fragments.signup.SignUpEmailFM
import team.tiktok.tiktokapp.fragments.signup.SignUpPhoneFM
import team.tiktok.tiktokapp.fragments.user.LikedUserVideosFM
import team.tiktok.tiktokapp.fragments.user.PrivateUserVideosFM
import team.tiktok.tiktokapp.fragments.user.SavedUserVideosFM
import team.tiktok.tiktokapp.fragments.user.UserVideosFM

class ProfileViewpagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserVideosFM()
            1 -> PrivateUserVideosFM()
            2 -> LikedUserVideosFM()
            3 -> SavedUserVideosFM()
            else -> UserVideosFM()
        }

    }

}