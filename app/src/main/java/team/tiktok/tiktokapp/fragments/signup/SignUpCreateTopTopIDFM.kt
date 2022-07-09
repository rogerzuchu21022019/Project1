package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.*
import team.tiktok.tiktokapp.databinding.FragmentSignUpCreateToptopIdBinding


class SignUpCreateTopTopIDFM : Fragment() {
    lateinit var binding: FragmentSignUpCreateToptopIdBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var databaseVideos: DatabaseReference
    val navArg: SignUpCreateTopTopIDFMArgs by navArgs()
    var list = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCreateToptopIdBinding.inflate(layoutInflater)

        auth = Firebase.auth
        CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
            clickButton()
        }

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

    private fun isChecking(isInbox:Boolean) {
        if (isInbox){
            findNavController().popBackStack(R.id.homeFM,false)
        }else{
            findNavController().popBackStack(R.id.inboxFM,false)
        }
    }

    private fun createUserData(
        email: String,
        password: String,
        topTopID: String,
        birth: String,
        uuid: String
    ) {


//        database.child(video.uidVideo!!).push()
//        val listVideo = mutableListOf(video)
//
//        database.child(video.uidVideo!!).setValue(listVideo)
//            .addOnCompleteListener {
//                Toast.makeText(requireContext(), "ok create video", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {  }

    }


    private fun createUserAuth(email: String, password: String, birth: String, topTopID: String) {
        database = Firebase.database.getReference("users")
        databaseVideos = Firebase.database.getReference("videos")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isComplete) {
                    var userUID = auth.currentUser!!.uid

                    val follower = Follower(uid = "", 0)
                    val following = Following(uid = "", 0)
                    val heart = Heart(id = 0, 0, "", "")
                    val comment = Comment(
                        uidComment = "",
                        message = "",
                        user = null,
                        fullName = "",
                        updateAt = "",
                        countComments = 0,
                        hearts = 0
                    )
                    val video = Video(
                        uidVideo = "HahaVideo",
                        title = "",
                        description = "Husky Ngu nguc",
                        url = "",
                        createAt = null,
                        updateAt = null
                    )

                    val user = User(
                        email = email,
                        fullName = topTopID,
                        topTopID = topTopID,
                        password = password,
                        birthDay = birth,
                    )


                    ///upload user from local to firebase realtimedatabase
                    database.child(user.topTopID).setValue(user)
                        .addOnCompleteListener {

                        }
                        .addOnFailureListener {
                        }
                    ///upload video to internal user object
                    val refVideoChild = Firebase.database.getReference("users")

                    refVideoChild.child(user.topTopID).child("videos").child(video.uidVideo!!)
                        .setValue(video)
                        .addOnCompleteListener {

                        }
                        .addOnFailureListener {

                        }

                    databaseVideos.child(video.uidVideo!!).setValue(video)
                        .addOnCompleteListener {

                        }
                        .addOnFailureListener {


                        }
                    isChecking(true)
                }
            }
            .addOnFailureListener {
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}