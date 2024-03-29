package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import team.tiktok.tiktokapp.R
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
        checkComeIn(true)
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
                findNavController().popBackStack()

            }
        }
        binding.btnCreate.apply {
            setOnClickListener {
                val topTopID = binding.edtTopTopID.text.toString().trim()
                if(TextUtils.isEmpty(topTopID)){
                    Toast.makeText(requireContext(),"Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
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
        if (findNavController().previousBackStackEntry!!.destination.id == R.id.addFM){
            findNavController().popBackStack()
        }else{
            val action = SignUpCreateTopTopIDFMDirections.actionSignUpCreateTopTopIDFMToEmptyFM()
            findNavController().navigate(action)
        }
    }


    private fun createUserAuth(email: String, password: String, birth: String, topTopID: String) {
        database = Firebase.database.getReference("users")
        databaseVideos = Firebase.database.getReference("videos")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                var userUID = auth.currentUser!!.uid

                val follower =
                    Follower(uid = "", subscribe = false, countFollowers = 12000, users = null)
                val following = Following(uid = "", 30, users = null)
                val comment = Comment(
                    uidComment = "",
                    message = "",
                    users = null,
                    fullName = "",
                    updateAt = "",
                    countComments = 0,
                    hearts = 1299
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
                    countHearts = 13000
                )
                ///upload user from local to firebase realtimedatabase
                database.child(user.uuid!!).setValue(user)
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

    override fun onStart() {
        super.onStart()
        checkComeIn(true)
    }

    override fun onStop() {
        super.onStop()
        auth.signOut()
    }
}