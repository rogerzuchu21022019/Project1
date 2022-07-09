package team.tiktok.tiktokapp.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
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
        CoroutineScope(IO).launch {
            clickButton()
        }

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

                createUserAuth(email, password,birth,topTopId)

            }
        }
    }

    private fun createUserData(email: String, password: String, topTopID: String, birth: String,uuid:String) {


//        database.child(video.uidVideo!!).push()
//        val listVideo = mutableListOf(video)
//
//        database.child(video.uidVideo!!).setValue(listVideo)
//            .addOnCompleteListener {
//                Toast.makeText(requireContext(), "ok create video", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {  }

    }


    private fun createUserAuth(email: String, password: String,birth: String,topTopID: String) {
        database = Firebase.database.getReference("users")
        databaseVideos = Firebase.database.getReference("videos")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isComplete) {
                    Toast.makeText(requireContext(), "ok", Toast.LENGTH_SHORT).show()
                    var userUID = auth.currentUser!!.uid
                    Toast.makeText(requireContext(), "ok $userUID", Toast.LENGTH_SHORT).show()

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
                    val video = Video(uidVideo = null,title ="HahaVideo", description =null, url = null, createAt = null, updateAt = null)
                    val listVideo = mutableSetOf(video)
                    var user = User(
                        email = email,
                        uuid = userUID,
                        password = password,
                        topTopID = topTopID,
                        fullName = topTopID,
                        birthDay = birth,
                        phone = "",
                        follower = follower,
                        imgUrl = "",
                        favorites = 0,
                        hearts = heart,
                        following = following,
                        profileUrl = "",
                        comment = null,
                        videos = video,
                        urlFollower = "",
                        urlFollowing = ""
                    )
                    
                    val toptopid = topTopID
                    ///upload user from local to firebase realtimedatabase
                    database.child(toptopid). setValue(user)
                        .addOnCompleteListener {
                            Toast.makeText(requireContext(), "create succesfullt", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "failure", Toast.LENGTH_SHORT).show()

                        }
                    ///upload video to internal user object
                    database.child(toptopid).child("videos").setValue(video)
                        .addOnCompleteListener {
                            Toast.makeText(requireContext(), "create video success", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "failure", Toast.LENGTH_SHORT).show()

                        }

                    databaseVideos.child(video.title!!).setValue(video)
                        .addOnCompleteListener {
                            Toast.makeText(requireContext(), "create video success", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "failure", Toast.LENGTH_SHORT).show()

                        }
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