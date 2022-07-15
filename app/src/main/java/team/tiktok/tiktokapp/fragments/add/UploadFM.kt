package team.tiktok.tiktokapp.fragments.add

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentUploadBinding


class UploadFM : Fragment() {
    lateinit var binding: FragmentUploadBinding
    private val IMAGE_REQ = 1
    lateinit var storageReference: StorageReference
    lateinit var databaseUser: DatabaseReference
    var videoPath: Uri? = null
    val fetchUuid = Firebase.database.getReference("users")
    val navArgs: UploadFMArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadBinding.inflate(layoutInflater)
        storageReference = FirebaseStorage.getInstance().reference.child("UsersFoler")
        loadVideoFromArgs()


        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_uploadFM_to_addFM)
            }
        }
        binding.btnNext.apply {
            setOnClickListener {
                isLogIn()
                Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun loadVideoFromArgs(): String {

        videoPath = Uri.parse(navArgs.videoPath)
        Log.d("UploadFM", "get videoPath success: ${videoPath!!.lastPathSegment}")

        binding.videoView.setVideoURI(videoPath)
        binding.videoView.start()
        binding.videoView.fitsSystemWindows = true
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            binding.videoView.pause()
        }, 2000)
        return videoPath!!.lastPathSegment!!
    }

    private fun isLogIn() {

        val auth = Firebase.auth
        if (auth.currentUser != null) {
            CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
                getDataOK()
                withContext(Dispatchers.Main) {
                    val handle = Handler(Looper.myLooper()!!)
                    handle.postDelayed({
                        navSignUp()
                        handleShowProgressBar()
                    }, 1000)
                    handleHideProgressBar()
                }
            }

        } else {
//            findNavController().popBackStack(R.id.signUpBottomSheetFM, false, true)
            val action = UploadFMDirections.actionUploadFMToSignUpBottomSheetFM()
            findNavController().navigate(action)

        }
    }

    private fun getDataOK() {
        val auth = Firebase.auth
        var isCheck: Boolean? = null
        val description = binding.edtDescription.text.toString().trim()
        val fetchVideos = Firebase.database.getReference("videos")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (element in snapshot.children) {
                    ///get uuid of user after loop cross user
                    var uuid = element.child("uuid").getValue(String::class.java)
                    if (auth.currentUser!!.uid == uuid) {
                        /// create keyVideo
                        val keyVideo = description

                        /// call variable reference to StorageReference and create path
                        val videoStorage =
                            storageReference.child("videos/" + loadVideoFromArgs())

                        /// putFile to storage into path which created
                        videoStorage.putFile(videoPath!!)
                            .addOnCompleteListener {
                                /// handle complete
                                Log.d(
                                    "UploadFM",
                                    "use upload task to put url into storageRef success: ${
                                        Uri.parse(loadVideoFromArgs())
                                    }"
                                )
                                if (it.isSuccessful) {
                                    /// when upload success. Use downloadUrl download url from StorageReference to fetchUuid
                                    videoStorage.downloadUrl
                                        .addOnCompleteListener { urlDownLoadFromStorage ->
                                            /// convert it to urlDownLoadFromStorage and get result of video.downloadUrl is variable result
                                            val url = urlDownLoadFromStorage.result.toString()

                                            fetchUuid.child(element.key!!)
                                                .addValueEventListener(object :
                                                    ValueEventListener {
                                                    override fun onDataChange(snapshotUser: DataSnapshot) {
                                                        val user = snapshotUser.getValue<User>()
                                                        val video = Video(
                                                            uidVideo = description,
                                                            description = description,
                                                            title = description,
                                                            url = url,
                                                            createAt = "",
                                                            updateAt = "",
                                                            user = user
                                                        )
                                                        Log.d(
                                                            "UploadFM",
                                                            "user load success: $user"
                                                        )

                                                        /// upload video to user information in fetchUuid
                                                        fetchUuid.child(element.key!!)
                                                            .child("videos")
                                                            .child(keyVideo).setValue(video)
                                                        /// set identifier for video in fetchVideos
                                                        fetchVideos.child(keyVideo).push().key
                                                        /// set video for fetchVideos
                                                        fetchVideos.child(keyVideo)
                                                            .setValue(video)
                                                    }

                                                    override fun onCancelled(error: DatabaseError) {
                                                    }

                                                })
                                            isCheck = true

                                        }
                                    isCheck = true
                                }
                                isCheck = true
                            }
                            .addOnFailureListener {
                                /// handle fail
                                Log.d("UploadFM", "put fail: ${it.message}")

                            }
                        /// set isCheck to out loop

                    }
                    if (isCheck == true) {
                        Log.d("UploadFM", "break: $isCheck")
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        fetchUuid.addValueEventListener(listener)
    }

    fun navSignUp() {
        if (findNavController().currentDestination?.id == R.id.uploadFM
            && findNavController().previousBackStackEntry!!.destination.id == R.id.addFM
        ) {
            val action = UploadFMDirections.actionUploadFMToEmptyFM()
            findNavController().navigate(action)
        } else if (findNavController().currentDestination?.id == R.id.uploadFM
            && findNavController().previousBackStackEntry!!.destination.id == R.id.signInContainerFM
        ) {
            handleShowProgressBar()
            val action = UploadFMDirections.actionUploadFMToEmptyFM()
            findNavController().navigate(action)
            handleHideProgressBar()
        }
    }

    fun handleShowProgressBar() {
        val handle = Handler(Looper.myLooper()!!)
        handle.postDelayed({
            binding.progressbar.visibility = View.VISIBLE
        }, 3000)
    }

    fun handleHideProgressBar() {
        binding.progressbar.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

    override fun onStart() {
        super.onStart()
    }
}