package team.tiktok.tiktokapp.fragments.forgotpass

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentForgotPaswordEmailBinding


class ForgotWithEmailFM : Fragment() {
   lateinit var binding: FragmentForgotPaswordEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPaswordEmailBinding.inflate(layoutInflater)
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_forgotWithEmailFM_to_signInContainerFM2)

            }
        }
        binding.btnSend.apply {
            setOnClickListener {
                resetPassEmail()
            }
        }
    }
    private  fun resetPassEmail(){
        val emailAddress = binding.edtEmail.text.toString().trim()
        if(TextUtils.isEmpty(emailAddress)){
            Toast.makeText(requireContext(), "Vui lòng nhập email.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress)
                .matches()
        ) {
            Toast.makeText(
                requireContext(),
                "Vui lòng điền đúng định dạng! Ví dụ abcyxz@gmail.com",
                Toast.LENGTH_SHORT
            ).show()
            return
        }


        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Reset thành công", Toast.LENGTH_SHORT).show()
                    val action = ForgotWithEmailFMDirections.actionForgotWithEmailFMToSignInContainerFM2()
                    findNavController().navigate(action)
                }
            }
            .addOnFailureListener { error->

                Toast.makeText(requireContext(),"${error.message}",Toast.LENGTH_SHORT).show()
                Log.d("Error","${error.message}")
            }

    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}