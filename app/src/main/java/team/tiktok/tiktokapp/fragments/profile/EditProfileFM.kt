package team.tiktok.tiktokapp.fragments.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.databinding.FragmentEditProfileBinding


class EditProfileFM : Fragment() {
    private val IMAGE_REQ = 1
    private var imagePath: Uri? = null
    lateinit var binding: FragmentEditProfileBinding
    lateinit var mDataBase:DatabaseReference
    val navArgs : EditProfileFMArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        clickImage()
        clickButton()
        updateUI()
        return binding.root
    }


    /// TODO: Get User from profile FM
    fun getUser(): User? {
        return navArgs.user!!
    }

    /// TODO: Update Information
    fun updateUI(){
        CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                mDataBase = Firebase.database.getReference("users")
                mDataBase.child(getUser()!!.uuid!!).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)!!
                        binding.user = user
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            }
        }
    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_editProfileFM_to_profileFM)
            }
        }
    }

    private fun clickImage() {
        binding.civAvatar.apply {
            setOnClickListener {
                requestPermission()
                Toast.makeText(requireActivity(), "OK", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvName.apply {
            setOnClickListener {
                val action = EditProfileFMDirections.actionEditProfileFMToUpdateFullname(binding.user)
                findNavController().navigate(action)
            }
        }
        binding.tvTikTokID.apply {
            setOnClickListener {
                val action = EditProfileFMDirections.actionEditProfileFMToUpdateTopTopID(binding.user)
                findNavController().navigate(action)
            }
        }
    }
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage()

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), IMAGE_REQ
            )
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*" // if you want to you can use pdf/gif/video
        intent.action = Intent.ACTION_GET_CONTENT
        someActivityResultLauncher.launch(intent)
    }


    private var someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data = result.data
            imagePath = data!!.data
            Picasso.get().load(imagePath).into(binding.civAvatar)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

}