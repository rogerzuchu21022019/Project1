package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.data.*
import team.tiktok.tiktokapp.databinding.FragmentSignUpCreateToptopIdBinding


class SignUpCreateTopTopIDFM : Fragment() {
    lateinit var binding: FragmentSignUpCreateToptopIdBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    val navArg: SignUpCreateTopTopIDFMArgs by navArgs()
    var list = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCreateToptopIdBinding.inflate(layoutInflater)

        auth = Firebase.auth
        clickButton()


        return binding.root
    }

    fun getArrSignUp(): Array<String> {
        return navArg.arrSignUp
    }

    private fun clickButton() {
        binding.btnCreate.apply {
            setOnClickListener {
                val topTopID = binding.edtTopTopID.text.toString().trim()
                list = getArrSignUp().toMutableList()
                list.add(3, topTopID)
                val birth = list[0].trim()
                val email = list[1].trim()
                val password = list[2].trim()
                val topTopId = list[3].trim()

                createUserAuth(email, password)
                createUserData(email,password,topTopId,birth)
                val action =
                    SignUpCreateTopTopIDFMDirections.actionSignUpCreateTopTopIDFMToSignInContainerFM()
                findNavController().navigate(action)
            }
        }
    }

    private fun createUserData(email: String, password: String, topTopID: String, birth: String) {
        database = Firebase.database.getReference("users")
        val uuid = auth.currentUser?.uid
        val follower = Follower(uid = "" , 0)
        val following = Following(uid = "", 0)
        val heart = Heart(null, null, "", "")
        val comment = Comment(null, null, user = null, fullName = "", updateAt = "", countComments = 0, hearts = 0)
        val video = Video(title = null, description = "", url = "", createAt = "", updateAt = "")
        val user = User(
            email = email,
            password = password,
            topTopID = topTopID,
            fullName = topTopID,
            birthDay = birth,
            uuid = uuid!!,
            phone = "",
            follower = follower,
            imgUrl = "",
            favorites = 0,
            hearts = heart,
            following = following,
            profileUrl = "",
            comment = comment,
            video = video,
            urlFollower = "",
            urlFollowing = ""
        )
        database.child(user.topTopID!!).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "ok create user", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {

            }
    }


    private fun createUserAuth(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isComplete) {
                    Toast.makeText(requireContext(), "ok", Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "failure", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}