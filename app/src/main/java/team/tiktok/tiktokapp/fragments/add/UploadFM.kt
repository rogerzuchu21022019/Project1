package team.tiktok.tiktokapp.fragments.add

import android.net.Uri
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentUploadBinding


class UploadFM : Fragment() {
    lateinit var binding: FragmentUploadBinding
    lateinit var storageReference: StorageReference
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
                val handle = Handler(Looper.myLooper()!!)
                handle.postDelayed({
                    isLogIn()
                }, 1000)
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
            uploadVideo()
            handleHideProgressBar()
        } else {
            val action = UploadFMDirections.actionUploadFMToSignUpBottomSheetFM()
            findNavController().navigate(action)
        }
    }

    private fun uploadVideo() {
        val auth = Firebase.auth
        val description = binding.edtDescription.text.toString().trim()

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(
                requireContext(),
                "Hãy viết content để mang lại thông điệp cho video của bạn",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val fetchVideos = Firebase.database.getReference("videos")

        val keyVideo = description

        /// TODO: Call variable reference to StorageReference and create path
        val videoStorage =
            storageReference.child("videos/" + loadVideoFromArgs())

        /// TODO: PutFile to storage into path which created
        videoStorage.putFile(videoPath!!)
            .addOnCompleteListener {
                ///TODO: Handle complete
                Log.d(
                    "UploadFM",
                    "use upload task to put url into storageRef success: ${
                        Uri.parse(loadVideoFromArgs())
                    }"
                )
                if (it.isSuccessful) {
                    ///TODO: When upload success. Use downloadUrl download url from StorageReference to fetchUuid
                    videoStorage.downloadUrl
                        .addOnCompleteListener { urlDownLoadFromStorage ->
                            ///TODO: Convert it to urlDownLoadFromStorage and get result of video.downloadUrl is variable result
                            val url = urlDownLoadFromStorage.result.toString()
                            fetchUuid.child(auth.currentUser!!.uid)
                                .addValueEventListener(object :
                                    ValueEventListener {
                                    override fun onDataChange(snapshotUser: DataSnapshot) {
                                        val user = snapshotUser.getValue<User>()
                                        val video = Video(
                                            uidVideo = description,
                                            description = description,
                                            url = url,
                                            createAt = "",
                                            updateAt = "",
                                            user = user
                                        )
                                        Log.d(
                                            "UploadFM",
                                            "user load success: $user"
                                        )

                                        ///TODO: Upload video to user information in fetchUuid
                                        fetchUuid.child(auth.currentUser!!.uid)
                                            .child("videos")
                                            .child(keyVideo).setValue(video)
                                        ///TODO: Set identifier for video in fetchVideos
                                        fetchVideos.child(keyVideo).push().key
                                        ///TODO: Set video for fetchVideos
                                        fetchVideos.child(keyVideo)
                                            .setValue(video)
                                        navSignUp()
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }

                                })

                        }
                }
            }
            .addOnFailureListener {
                ///TODO: Handle fail
                Log.d("UploadFM", "put fail: ${it.message}")
            }
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
        }
    }

    fun handleShowProgressBar() {
        val handle = Handler(Looper.myLooper()!!)
        handle.postDelayed({
            binding.progressbar.visibility = View.VISIBLE
            val action = UploadFMDirections.actionUploadFMToEmptyFM()
            findNavController().navigate(action)
            handleHideProgressBar()
        }, 2000)
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