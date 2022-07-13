package team.tiktok.tiktokapp.adapter.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import team.tiktok.tiktokapp.fragments.profile.UserProfileVideosFM
import team.tiktok.tiktokapp.fragments.user.LikedUserVideosFM
import team.tiktok.tiktokapp.fragments.user.PrivateUserVideosFM
import team.tiktok.tiktokapp.fragments.user.SavedUserVideosFM

class DetailUserViewpagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserProfileVideosFM()
            1 -> PrivateUserVideosFM()
            2 -> LikedUserVideosFM()
            3 -> SavedUserVideosFM()
            else -> UserProfileVideosFM()
        }

    }

}