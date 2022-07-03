package team.tiktok.tiktokapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.squareup.picasso.Picasso
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding  : ActivityMainBinding
    private val IMAGE_REQ = 1
    private var imagePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.fmNavHostGraph)
        binding.navBot.setupWithNavController(navController)

//        title and icon settings are displayed together
        binding.navBot.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED

    }

}