package team.tiktok.tiktokapp.fragments.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.databinding.FragmentInboxBinding
import team.tiktok.tiktokapp.fragments.profile.ProfileFMDirections


class InboxFM : Fragment() {
   lateinit var binding:FragmentInboxBinding
   lateinit var auth: FirebaseAuth
   lateinit var database:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInboxBinding.inflate(layoutInflater)
        isLogIn()
        return binding.root
    }
    fun navSignUp() {
        val action = InboxFMDirections.actionInboxFMToSignUpBottomSheetFM()
        findNavController().navigate(action)
    }

    private fun isLogIn() {
        auth = Firebase.auth
        if (auth.currentUser!=null){
            checkExist(auth.currentUser!!.uid)
            Toast.makeText(
                requireContext(),
                "ai do login",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            navSignUp()
        }
    }
    private fun checkExist(uid: String) {
        database = Firebase.database.getReference("users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (element in snapshot.children){
                        var user = element.getValue(User::class.java)
                        if (uid == user!!.uuid) {
                            Toast.makeText(
                                requireContext(),
                                "ok ${user.topTopID}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

}