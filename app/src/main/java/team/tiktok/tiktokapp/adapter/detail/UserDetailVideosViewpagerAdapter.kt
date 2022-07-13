package team.tiktok.tiktokapp.adapter.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import team.tiktok.tiktokapp.fragments.user.LikedUserVideosFM
import team.tiktok.tiktokapp.fragments.user.PrivateUserVideosFM
import team.tiktok.tiktokapp.fragments.user.UserDetailVideosFM

class UserDetailVideosViewpagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserDetailVideosFM()
            1 -> PrivateUserVideosFM()
            2 -> LikedUserVideosFM()
            else -> UserDetailVideosFM()
        }

    }

}