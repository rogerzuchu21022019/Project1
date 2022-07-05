package team.tiktok.tiktokapp.fragments.add

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
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentAddBinding
import team.tiktok.tiktokapp.databinding.FragmentUploadBinding


class UploadFM : Fragment() {
   lateinit var binding:FragmentUploadBinding
    private val IMAGE_REQ = 1
    private var imagePath: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadBinding.inflate(layoutInflater)
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
                findNavController().navigate(R.id.action_uploadFM_to_signInContainerFM)
            }
        }
        binding.videoView.apply {
            setOnClickListener {
                requestPermissionVideo()
            }
        }
    }


    private fun requestPermissionVideo() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            selectVideo()

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), IMAGE_REQ
            )
        }
    }
    private fun selectVideo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*" // if you want to you can use pdf/gif/video
        intent.action = Intent.ACTION_GET_CONTENT
        someActivityResultLauncher.launch(intent)
    }


    private var someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data = result.data
            imagePath = data!!.data
            binding.videoView.setVideoURI(imagePath)
            binding.videoView.start()
            binding.videoView.fitsSystemWindows = true
//            Picasso.get().load(imagePath).into(binding.civAvatar)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}