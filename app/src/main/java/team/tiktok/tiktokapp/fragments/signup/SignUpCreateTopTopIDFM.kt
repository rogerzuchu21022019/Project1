package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.data.Comment
import team.tiktok.tiktokapp.data.Follower
import team.tiktok.tiktokapp.data.Following
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.databinding.FragmentSignUpCreateToptopIdBinding


class SignUpCreateTopTopIDFM : Fragment() {
    lateinit var binding: FragmentSignUpCreateToptopIdBinding
    lateinit var database: DatabaseReference
    lateinit var databaseVideos: DatabaseReference
    lateinit var auth: FirebaseAuth

    val navArg: SignUpCreateTopTopIDFMArgs by navArgs()
    var list = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCreateToptopIdBinding.inflate(layoutInflater)
        clickButton()
        auth = Firebase.auth
        return binding.root
    }

    fun getArrSignUp(): Array<String> {
        return navArg.arrSignUp
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {

            }
        }
        binding.btnCreate.apply {
            setOnClickListener {
                val topTopID = binding.edtTopTopID.text.toString().trim()
                list = getArrSignUp().toMutableList()
                list.add(3, topTopID)
                val birth = list[0].trim()
                val email = list[1].trim()
                val password = list[2].trim()
                val topTopId = list[3].trim()
                createUserAuth(email, password, birth, topTopId)

            }
        }

    }

    private fun navDirection() {
        val action = SignUpCreateTopTopIDFMDirections.actionSignUpCreateTopTopIDFMToSignInContainerFM()
        findNavController().navigate(action)
    }


    private fun createUserAuth(email: String, password: String, birth: String, topTopID: String) {
        database = Firebase.database.getReference("users")
        databaseVideos = Firebase.database.getReference("videos")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                var userUID = auth.currentUser!!.uid

                val follower =
                    Follower(uid = "", subscribe = false, countFollowers = 0, users = null)
                val following = Following(uid = "", 0, users = null)
                val comment = Comment(
                    uidComment = "",
                    message = "",
                    users = null,
                    fullName = "",
                    updateAt = "",
                    countComments = 0,
                    hearts = 0
                )
                val user = User(
                    email = email,
                    fullName = topTopID,
                    topTopID = topTopID,
                    password = password,
                    birthDay = birth,
                    uuid = userUID,
                    videos = null,
                    follower = follower,
                    following = following,
                )
                ///upload user from local to firebase realtimedatabase
                database.child(user.topTopID!!).setValue(user)
                    .addOnCompleteListener {
                        navDirection()
                        Log.d("CreateTopTop", "Create Account Successfully: ${it.isSuccessful}")
                    }
                    .addOnFailureListener {
                        Log.d("CreateTopTop", "Error set Value: ${it.message}")
                    }
            }
            .addOnFailureListener {
                Log.d("CreateTopTop", "Error auth: ${it.message}")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

    override fun onStop() {
        super.onStop()
        auth.signOut()
    }
}