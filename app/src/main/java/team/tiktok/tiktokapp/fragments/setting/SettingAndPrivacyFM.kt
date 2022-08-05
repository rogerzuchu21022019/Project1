package team.tiktok.tiktokapp.fragments.setting

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSettingAndPrivacyBinding


class SettingAndPrivacyFM : Fragment() {
   lateinit var binding: FragmentSettingAndPrivacyBinding
   lateinit var auth :FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingAndPrivacyBinding.inflate(layoutInflater)
        clickButton()
        auth = Firebase.auth
        checkComeIn(true)
        isLogIn()
        return binding.root
    }
    private fun isLogIn() {
        auth = Firebase.auth
        if (auth.currentUser!=null){
            binding.tvSignOut.visibility = View.VISIBLE
            binding.btnSignUp.visibility = View.GONE
        }else{
            binding.tvSignOut.visibility = View.GONE
            binding.btnSignUp.visibility = View.VISIBLE
        }

    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_settingAndPrivacyFM_to_profileFM)
            }
        }
        binding.tvManageAccount.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_settingAndPrivacyFM_to_manageAccountFM)
            }
        }
        binding.tvSignOut.apply {
            setOnClickListener {
                if (auth.currentUser!=null){
                    auth.signOut()
                    Toast.makeText(requireContext(),"sign out",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_settingAndPrivacyFM_to_profileFM)
                }else{
                    Toast.makeText(requireContext(),"null",Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnSignUp.apply {
            setOnClickListener {
                val action = SettingAndPrivacyFMDirections.actionSettingAndPrivacyFMToSignUpBottomSheetFM()
                findNavController().navigate(action)
            }
        }
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
        checkComeIn(false)

        binding == null
    }
}