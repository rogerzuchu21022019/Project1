package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
//import me.ibrahimsn.lib.SmoothBottomBar
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.signup.SignUpViewpagerAdapter
import team.tiktok.tiktokapp.databinding.FragmentSignUpContainerBinding


class SignUpContainerFM : Fragment() {
    lateinit var binding: FragmentSignUpContainerBinding
    lateinit var adapter: SignUpViewpagerAdapter
    val navArg: SignUpContainerFMArgs by navArgs()

    companion object {
        val FRAGMENT_PHONE: Int = 0
        val FRAGMENT_EMAIL: Int = 1

        var mFragmentCurrent = FRAGMENT_PHONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkComeIn(true)
        binding = FragmentSignUpContainerBinding.inflate(layoutInflater)
        initViewPager()
        clickButton()
        getBirth()
        return binding.root
    }


    private fun getBirth(): String {
//        Toast.makeText(requireContext(), "${navArg.birth}", Toast.LENGTH_SHORT).show()
        return navArg.birth
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tab,
            binding.vpSignUp
        ) { tab, position ->
            if (position == 0) {
                tab.text = "Phone"
            } else if (position == 1) {
                tab.text = "Email"
            }
        }.attach()
    }

    private fun initViewPager() {
        adapter = SignUpViewpagerAdapter(this)
        binding.vpSignUp.adapter = adapter
        initTabLayout()
        ///click each fragment in viewpager
        binding.vpSignUp.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 1) {
                    mFragmentCurrent = FRAGMENT_EMAIL
                    if (binding.vpSignUp.currentItem == FRAGMENT_EMAIL) {
                        ///assign btn and edt from email
                        var btnSignUp: View = view!!.findViewById(R.id.btnSignUp)
                        var edtEmail: EditText = view!!.findViewById(R.id.edtEmail)
                        var checkBox: CheckBox = view!!.findViewById(R.id.chkSignUp)

                        btnSignUp.setOnClickListener {
                            if (TextUtils.isEmpty(edtEmail.text.toString())) {
                                Toast.makeText(
                                    requireContext(),
                                    "Vui lòng điền đầy đủ thông tin",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@setOnClickListener
                            }
                            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString())
                                    .matches()
                            ) {
                                Toast.makeText(
                                    requireContext(),
                                    "Vui lòng điền đúng định dạng! Ví dụ abcyxz@gmail.com",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@setOnClickListener
                            }
                            val email = edtEmail.text.toString().trim()
                            if (checkBox.isChecked && !TextUtils.isEmpty(email)) {
                                val arrSignUp = mutableListOf(getBirth())
                                arrSignUp.add(1, email)
                                var action =
                                    SignUpContainerFMDirections.actionSignUpContainerFMToSignUpCreatePassFM(
                                        arrSignUp.toTypedArray()
                                    )
                                findNavController().navigate(action)
//                                Toast.makeText(requireContext(), "$arrSignUp", Toast.LENGTH_SHORT)
//                                    .show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Vui lòng đồng ý với các điều khoản của chúng tôi",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        }
                    }
                }
            }
        })
    }

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        } else {
            val navBot = requireActivity().findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        checkComeIn(false)
        binding == null
    }
}