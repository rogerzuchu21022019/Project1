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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentAddBinding
import team.tiktok.tiktokapp.databinding.FragmentSettingAndPrivacyBinding


class AddFM : Fragment() {
   lateinit var binding:FragmentAddBinding
    private val IMAGE_REQ = 1
    private var imagePath: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)
        requestPermissionCamera()
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.btnSound.apply {
            setOnClickListener {
                Toast.makeText(requireContext(),"Sound",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun requestPermissionCamera() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            accessCamera()

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.CAMERA
                ), IMAGE_REQ
            )
        }
    }
    private fun accessCamera() {
        val intent = Intent()
//        intent.type = "image/*" // if you want to you can use pdf/gif/video
        intent.action = android.provider.MediaStore.ACTION_IMAGE_CAPTURE
        someActivityResultLauncher.launch(intent)
    }


    private var someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data = result.data
            imagePath = data!!.data
//            Picasso.get().load(imagePath).into(binding.civAvatar)
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }
}