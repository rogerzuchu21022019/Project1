package team.tiktok.tiktokapp.activities

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding  : ActivityMainBinding
    private val IMAGE_REQ = 1
    private var imagePath: Uri? = null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.fmNavHostGraph)
        binding.navBot.setupWithNavController(navController)

//        title and icon settings are displayed together
        binding.navBot.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.navBot.itemIconTintList = null

    }



}