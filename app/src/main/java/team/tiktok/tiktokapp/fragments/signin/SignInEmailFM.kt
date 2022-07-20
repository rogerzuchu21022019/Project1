package team.tiktok.tiktokapp.fragments.signin

//import me.ibrahimsn.lib.SmoothBottomBar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSigninEmailBinding


class SignInEmailFM : Fragment() {
    lateinit var binding: FragmentSigninEmailBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninEmailBinding.inflate(layoutInflater)
        clickButton()
        checkComeIn(true)
        return binding.root
    }

    private fun clickButton() {
        auth = Firebase.auth
        binding.btnSignIn.apply {
            setOnClickListener {

                val email = binding.edtEmail.text.toString().trim()
                val password = binding.edtPassword.text.toString().trim()
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(requireContext(), "Please Fill Information", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "SignIn OK", Toast.LENGTH_SHORT).show()
                        callOnFirebaseAndFetchUser(email = email, password = password)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "SignIn Fail", Toast.LENGTH_SHORT).show()
                    }

            }

        }
        binding.tvForgotPassword.apply {
            setOnClickListener {
                Toast.makeText(requireContext(), "click1", Toast.LENGTH_SHORT).show()
                val action =
                    SignInContainerFMDirections.actionSignInContainerFMToChooseEmailOrPhoneBottomSheetFM()
                findNavController().navigate(action)
            }
        }
    }

    private fun callOnFirebaseAndFetchUser(email: String, password: String) {
        database = Firebase.database.getReference("users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    snapshot.children.forEach {
                        val email1 = it.child("email").getValue(String::class.java)
                        if (email == email1) {
                            Toast.makeText(
                                requireContext(),
                                "equal email = $email1",
                                Toast.LENGTH_SHORT
                            ).show()
                            val handle = Handler(Looper.myLooper()!!)
                            handle.postDelayed({
                                binding.progressbar.visibility = View.VISIBLE
                                navDirection()
                            }, 1000)
                            binding.progressbar.visibility = View.GONE
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("CHECKUser", "${error.message}")
            }
        })

    }

    private fun navDirection() {
        if (findNavController().currentDestination!!.id == R.id.signInEmailFM &&
                findNavController().previousBackStackEntry!!.destination.id == R.id.signUpCreateTopTopIDFM){
            val action = SignInContainerFMDirections.actionSignInContainerFMToEmptyFM()
            findNavController().navigate(action)
        }




//        if (findNavController().currentDestination?.id == R.id.signInEmailFM || findNavController().currentDestination?.id == R.id.signInContainerFM
//            || findNavController().previousBackStackEntry!!.destination.id == R.id.profileFM){
////            val action = SignInContainerFMDirections.actionSignInContainerFMToProfileFM(null)
////            findNavController().navigate(R.id.action_signInContainerFM_to_profileFM)
//            findNavController().popBackStack(R.id.profileFM,true,false)
//
//            Toast.makeText(requireContext(), "1", Toast.LENGTH_SHORT).show()
//
//        }
//        else if(findNavController().currentDestination?.id == R.id.signInEmailFM ||findNavController().currentDestination?.id == R.id.signInContainerFM
//            || findNavController().previousBackStackEntry!!.destination.id == R.id.inboxFM){
////            findNavController().navigate(R.id.action_signInContainerFM_to_inboxFM)
//            findNavController().popBackStack(R.id.inboxFM,true,false)
//
//            Toast.makeText(requireContext(), "2", Toast.LENGTH_SHORT).show()
//
//        }else if(findNavController().currentDestination?.id == R.id.signInEmailFM ||findNavController().currentDestination?.id == R.id.signInContainerFM
//            && findNavController().previousBackStackEntry!!.destination.id == R.id.settingAndPrivacyFM){
////            findNavController().navigate(R.id.action_signInContainerFM_to_profileFM)
//            findNavController().popBackStack(R.id.settingAndPrivacyFM,true,false)
//
//            Toast.makeText(requireContext(), "3", Toast.LENGTH_SHORT).show()
//
//        }else if(findNavController().currentDestination?.id == R.id.signInEmailFM ||findNavController().currentDestination?.id == R.id.signInContainerFM
//            && findNavController().previousBackStackEntry!!.destination.id == R.id.homeFM){
//            val action = SignInContainerFMDirections.actionSignInContainerFMToHomeFM()
//            findNavController().popBackStack(R.id.homeFM,true,true)
//
////            findNavController().navigate(R.id.action_signInContainerFM_to_homeFM)
//            Toast.makeText(requireContext(), "4", Toast.LENGTH_SHORT).show()
//
//        }else if(findNavController().currentDestination?.id == R.id.signInContainerFM
//            && findNavController().previousBackStackEntry!!.destination.id == R.id.uploadFM){
//            val action = SignInContainerFMDirections.actionSignInContainerFMToUploadFM("")
//            findNavController().popBackStack(R.id.uploadFM,false,true)
//            Toast.makeText(requireContext(), "5", Toast.LENGTH_SHORT).show()
//
//        }
    }

    private fun checkComeIn(isComeIn: Boolean) {
        if (isComeIn) {
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.GONE
        } else {
            val navBot = requireActivity()!!.findViewById<AnimatedBottomBar>(R.id.navBot)
            navBot.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
        checkComeIn(true)
    }

    override fun onStart() {
        super.onStart()
    }

}