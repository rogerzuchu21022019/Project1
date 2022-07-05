package team.tiktok.tiktokapp.adapter.signin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import team.tiktok.tiktokapp.fragments.signin.SignInEmailFM
import team.tiktok.tiktokapp.fragments.signin.SignInPhoneFM
import team.tiktok.tiktokapp.fragments.signup.SignUpEmailFM
import team.tiktok.tiktokapp.fragments.signup.SignUpPhoneFM

class SignInViewpagerAdapter(fragmentManager: FragmentManager, lifecycler: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycler) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignInPhoneFM()
            1 -> SignInEmailFM()
            else -> SignInEmailFM()
        }

    }

}